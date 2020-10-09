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

class DeleteController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    private def _deleteGame(gameID: String): Future[Option[Boolean]] = {
        BSONObjectID.parse(gameID) match {
            case Failure(e) => Future { None }
            case Success(id) => gamesCollection.flatMap(
                _.delete
                .one(BSONDocument("_id" -> id))
                .map(_.n > 0)
                .map(Some(_))
            )
        }
    }

    private def deleteGameFiles(gameID: String): Boolean = {
        val response: HttpResponse[String] = Http("http://api-files:9090/" + gameID).method("DELETE").asString
        response.code match {
            case 200 => true
            case _ => false
        }
    }

    // TODO:
    // Use concurrency for DB operation and HTTP request
    // Now they're blocking

    def deleteGame(gameID: String) = Action.async {
        isValidGameID(gameID) match {
            case false => Future { BadRequest("Invalid gameID") }
            case true => _deleteGame(gameID).map {
                case None => BadRequest("Something went wrong")
                case Some(x) if x == false => BadRequest("Delete failed")
                case Some(y) if y == true => deleteGameFiles(gameID) match {
                    case false => BadRequest("Something went wrong")
                    case true => Ok("Success")
                }
            }
        }
    }
}
