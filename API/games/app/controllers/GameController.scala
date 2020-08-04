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

class GameController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def createGame() = Action.async(parse.json) { request: Request[JsValue] =>
        
        val gameName: String = (request.body.as[JsObject] \ "gameName").validate[String].getOrElse("")

        def isNameFree(gameName: String): Future[Boolean] = gamesCollection.flatMap(
            _.find(BSONDocument("name" -> gameName)).one[BSONDocument].map {
                case Some(game) => false
                case None => true
            }
        )

        def create(gameName: String): Future[Int] = {
            val newGame = BSONDocument(
                "name" -> gameName, 
                "mainFile" -> "",
                "resourceFiles" -> BSONArray()
            )
            gamesCollection.flatMap(
                _.insert.one(newGame).map(_.n)
            )
        }

        isNameFree(gameName).flatMap {
            case false => Future { Conflict("Game with the given name already exists") }
            case true => create(gameName).map {
                case x if x > 0 => Ok("")
                case _ => InternalServerError("Something went wrong")
            }
        }
    }

    def updateGame() = Action.async(parse.json) { request: Request[JsValue] =>

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
            println("gameID:")
            println(gameID)
            println("newGameName:")
            println(newGameName)
            println("files:")
            println(removedResourceFiles)

            gamesCollection.flatMap(
                _.find(BSONDocument("id" -> gameID)).one[JsObject](ReadPreference.primary)
            ).map { g =>
                println("HERE:")
                println(g)
            }

            /*_.find(BSONDocument())
            .cursor[JsObject](ReadPreference.primary)
            .collect[List](-1, Cursor.FailOnError[List[JsObject]]())

            gamesCollection.def isNameFree(gameName: String): Future[Boolean] = {
            val gameSelector = BSONDocument("name" -> gameName)
            gamesCollection.flatMap(
                _.find(gameSelector).one[BSONDocument].map {
                    case Some(game) => false
                    case None => true
                }
            )*/

        }

        // Find resourceFiles and perform update

        /*val gameID: Try[BSONObjectID] = BSONObjectID.parse(
            (request.body.as[JsObject] \ "gameID").validate[String].getOrElse("")
        ) 
        val newGameName: String = (request.body.as[JsObject] \ "gameName").validate[String].getOrElse("")
        val deletedResourceFiles: Seq[String] = 
            (request.body.as[JsObject] \ "removedResourceFiles").validate[Seq[String]].getOrElse(Seq.empty[String])
*/
        /*for {
            gameID <- BSONObjectID.parse((request.body.as[JsObject] \ "gameID").validate[String].getOrElse(""))
            //newGameName <- (request.body.as[JsObject] \ "gameName").validate[String]
            //resourceFiles: JsResult[Seq[String]] = (game \ "resourceFiles").validate[Seq[String]]
        } yield {
            println("\n\nID:")
            println(gameID)
            println("name:")
            //println(newGameName)
            println("\n\n")
        }*/

        //println("ASDASD")

        // GameName
        // New mainFile -> Handle with gameFiles
        // Remove resourceFiles

        /*gameID match {
            case Failure(e) => println("Invalid gameID")
            case Success(gameID) => newGameName match {
                case newGameName: String if newGameName.length > 0 => 
                case _ => 
            }
        }*/

        Future.successful(Ok(""))
    }

    def deleteGame() = Action.async(parse.json) { request: Request[JsValue] =>

        val gameID: Try[BSONObjectID] = BSONObjectID.parse(
            (request.body.as[JsObject] \ "gameID").validate[String].getOrElse("")
        )

        def delete(gameID: BSONObjectID) = {
            val gameSelector = BSONDocument("_id" -> gameID)
            gamesCollection.flatMap(
                _.delete.one(gameSelector).map(_.n)
            )
        }

        gameID match {
            case Failure(e) =>  Future { BadRequest("Invalid gameID") }
            case Success(gameID) => delete(gameID).map {
                case x if x > 0 => Ok("")
                case _ => NotFound("No games found with the given gameID")
            }  
        }
    }
}
