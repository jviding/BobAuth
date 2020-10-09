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

class GetController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    private def findOneGameByID(gameID: String): Future[Option[JsObject]] = {
        BSONObjectID.parse(gameID) match {
            case Failure(e) => Future { None }
            case Success(id) => gamesCollection.flatMap(
                _.find(BSONDocument("_id" -> id))
                .cursor[JsObject](ReadPreference.primary)
                .headOption
            )
        }
    }

    private def getGameFiles(gameID: String): Option[JsValue] = {
        val response: HttpResponse[String] = Http("http://api-files:9090/" + gameID).asString
        response.code match {
            case 200 => Some(Json.parse(response.body))
            case _ => None
        }
    }

    // TODO:
    // Use concurrency for DB operation and HTTP request
    // Now they're blocking

    def getGame(gameID: String) = Action.async {
        isValidGameID(gameID) match {
            case false => Future { BadRequest("Invalid gameID") }
            case true => findOneGameByID(gameID).map {
                case None => BadRequest("Game not found")
                case Some(game) => {
                    getGameFiles(gameID) match {
                        case None => BadRequest("Something went wrong")
                        case Some(files) => Ok(
                            Json.obj(
                                "id" -> (game \ "_id" \ "$oid").as[String],
                                "name" -> (game \ "name").as[String],
                                "mainFile" -> (files \ "main").as[JsArray],
                                "resourceFiles" -> (files \ "resources").as[JsArray]
                            )
                        )
                    }
                }
            }
        }
    }
}
