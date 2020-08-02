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
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._


class PlayerController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def playersCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("players"))

    def load(userID: String, gameName: String) = Action.async {

        val selector = BSONDocument("userID" -> userID, "gameName" -> gameName)
        val projection = BSONDocument("_id" -> 0, "gameState" -> 1)

        val playerOption: Future[Option[JsObject]] = playersCollection.flatMap(
            _.find(selector, projection).one[JsObject](ReadPreference.primary)
        )

        val gameStateOption: Future[Any] = playerOption.map {
            case Some(game) => game \ "gameState"
            case None => None
        }

        gameStateOption.map {
            case JsDefined(gameState) => Ok(gameState)
            case undefined: JsUndefined => NotFound
            case None => NotFound
        }
    }

    def save(userID: String, gameName: String) = Action.async(parse.json) { request: Request[JsValue] =>

        val gameState: JsObject = request.body.as[JsObject]

        val selector = BSONDocument("userID" -> userID, "gameName" -> gameName)
        val update = BSONDocument("$set" -> BSONDocument("gameState" -> gameState))
        val projection: Option[BSONDocument] = Some(BSONDocument("_id" -> 0, "gameState" -> 1))

        val gameOption: Future[Option[JsObject]] = playersCollection.flatMap(
            _.findAndUpdate(
                selector,
                update,
                fetchNewObject = true,
                upsert = true,
                fields = projection
            )
            .map(_.result[JsObject])
        )

        val gameStateOption: Future[Any] = gameOption.map {
            case Some(game) => game \ "gameState"
            case None => None
        }

        gameStateOption.map {
            case JsDefined(gameState) => Ok(gameState)
            case undefined: JsUndefined => NotFound
            case None => NotFound
        }
     }

}
