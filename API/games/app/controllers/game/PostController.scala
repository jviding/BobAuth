package controllers.game

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

class PostController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    private def isNameFree(gameName: String): Future[Boolean] = gamesCollection.flatMap(
        _.find(BSONDocument("name" -> gameName))
        .one[BSONDocument]
        .map {
            case Some(item) => false
            case None => true
        }
    )

    private def _createGame(gameName: String): Future[Option[BSONObjectID]] = {
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

    private def createGameDirectory(gameID: String): Boolean = {
        val response: HttpResponse[String] = Http("http://api-files:9090/" + gameID).method("POST").asString
        response.code match {
            case 200 => true
            case _ => false
        }
    }

    def createGame(gameName: String) = Action.async {
        isValidGameName(gameName) match {
            case false => Future { BadRequest("Invalid game name") }
            case true => isNameFree(gameName).flatMap {
                case false => Future { Conflict("Game name is already in use") }
                case true => { _createGame(gameName).map {
                    case None => BadRequest("Something went wrong")
                    case Some(id) => createGameDirectory(id.stringify) match {
                        case false => BadRequest("Something went wrong")
                        case true => Ok("Success")
                    }
                }}
            }
        }
    }
}
