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

        def isNameFree(gameName: String): Future[Boolean] = {
            val gameSelector = BSONDocument("name" -> gameName)
            gamesCollection.flatMap(
                _.find(gameSelector).one[BSONDocument].map {
                    case Some(game) => false
                    case None => true
                }
            )
        }

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

    def updateGame(gameID: String, gameName: String) {
        // GameName
        // New mainFile -> Handle with gameFiles
        // Remove resourceFiles
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
