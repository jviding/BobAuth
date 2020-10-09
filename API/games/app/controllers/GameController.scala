package controllers

import javax.inject._

import scala.concurrent.{ ExecutionContext, Future }

import play.api._
import play.api.mvc._
import play.api.libs.json._

import play.modules.reactivemongo.{
    MongoController,
    ReactiveMongoApi,
    ReactiveMongoComponents
}

import reactivemongo.api.bson._
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.Cursor
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.api.commands.WriteResult

import scala.util.{ Try, Success, Failure }

import scalaj.http._
  
import lib.Validator._

class GameController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def createGame(gameName: String) = Action.async {
        isValidGameName(gameName) match {
            case false => Future { BadRequest("Invalid game name") }
            case true => isNameFree(gameName).flatMap {
                case false => Future { Conflict("Game name is already in use") }
                case true => { create(gameName).flatMap {
                    case None => Future { BadRequest("Something went wrong") }
                    case Some(id) => {
                        val res: HttpResponse[String] = Http("http://api-files:9090/" + id.stringify).method("POST").asString
                        println(res)
                    
                        Future { Ok("") }
                    }
                }}
            }
        }
    }

    def updateGame() = Action.async(parse.json) { request: Request[JsValue] =>

        // Keep file info only in FS, not here
        // Here track only filename and other relevant info


        // TODO: REMOVE STORED RESOURCE FILES 
        // Not only from filenames from Game object!!!

        val gameID: Try[BSONObjectID] = BSONObjectID.parse(
            (request.body.as[JsObject] \ "gameID").validate[String].getOrElse("")
        ) match { 
            case Success(id) => Success(id)
            case Failure(e) => Failure(new Exception("Invalid gameID")) 
        }
        
        val newGameName: Try[String] = (request.body.as[JsObject] \ "newGameName").validate[String].getOrElse("") match {
            case str if str.length > 0 => Success(str)
            case _ => Failure(new Exception("New game name cannot be empty"))
        }

        val removedResourceFiles: Seq[String] =
            (request.body.as[JsObject] \ "removedResourceFiles").validate[Seq[String]].getOrElse(Seq.empty[String])

        for {
            gameID <- gameID
            newGameName <- newGameName
        } yield {
            gamesCollection.flatMap(
                _.find(BSONDocument("_id" -> gameID)).one[JsObject](ReadPreference.primary)
            ).map {
                case Some(game) => Success((game \ "resourceFiles").validate[Seq[String]].getOrElse(Seq.empty[String]))
                case None => Failure(new Exception("No games found with the given gameID"))
            }.map {
                case Success(resourceFiles) => Success(resourceFiles.filter( removedResourceFiles.indexOf(_) == -1 ))
                case Failure(e) => Failure(e)
            }.map {
                case Failure(e) => Failure(e)
                case Success(resourceFiles) => gamesCollection.flatMap {

                    println("ResourceFiles:")
                    println(resourceFiles)

                    _.findAndUpdate(
                        BSONDocument("_id" -> gameID),
                        BSONDocument("name" -> newGameName, "resourceFiles" -> resourceFiles),
                        fetchNewObject = true
                    ).map(_.result[JsObject]).map {
                        case Some(item) => println(item)
                        case None => println("NONE")
                    }
                }
            }

        }

        Future.successful(Ok(""))
    }

    def deleteGame() = Action.async(parse.json) { request: Request[JsValue] =>
        BSONObjectID.parse(
            (request.body.as[JsObject] \ "gameID")
            .validate[String]
            .getOrElse("")
        ) match {
            case Failure(e) => Future { BadRequest("Invalid gameID") }
            case Success(gameID) => delete(gameID).map {
                case false => NotFound("No games found with the given gameID")
                case true => Ok("")
            } 
        }
    }

    private def isNameFree(gameName: String): Future[Boolean] = gamesCollection.flatMap(
        _.find(BSONDocument("name" -> gameName))
        .one[BSONDocument]
        .map {
            case Some(game) => false
            case None => true
        }
    )

    private def create(gameName: String): Future[Option[BSONObjectID]] = {
        val id = BSONObjectID.generate()
        gamesCollection.flatMap(
            _.insert
            .one(BSONDocument(
                "_id" -> id,
                "name" -> gameName,
            ))
            .map(_.n match {
                case x if x > 0 => Some(id)
                case _ => None
            })
        )
    }

    private def delete(gameID: BSONObjectID): Future[Boolean] = gamesCollection.flatMap(
        _.delete
        .one(BSONDocument("_id" -> gameID))
        .map(_.n > 0)
    )

}
