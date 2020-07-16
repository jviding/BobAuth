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


class GameController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def load(userID: String, gameName: String) = Action.async {

        val selector = BSONDocument("userID" -> userID, "gameName" -> gameName)
        val projection = BSONDocument("_id" -> 0, "gameState" -> 1)

        val gameOption: Future[Option[JsObject]] = gamesCollection.flatMap(
            _.find(selector, projection).one[JsObject](ReadPreference.primary)
        )

        gameOption.map { option => 
            val game: JsObject = option.get
            val gameState: JsValue = (game \ "gameState").get
            Ok(gameState)
        }
    }

    def save(userID: String, gameName: String) = Action.async(parse.json) { request: Request[JsValue] =>

        val gameState: JsObject = request.body.as[JsObject]

        val selector = BSONDocument("userID" -> userID, "gameName" -> gameName)
        val update = BSONDocument("$set" -> BSONDocument("gameState" -> gameState))
        val projection: Option[BSONDocument] = Some(BSONDocument("_id" -> 0, "gameState" -> 1))

        val gameOption: Future[Option[JsObject]] = gamesCollection.flatMap(
            _.findAndUpdate(
                selector,
                update,
                fetchNewObject = true,
                upsert = true,
                fields = projection
            )
            .map(_.result[JsObject])
        )

        gameOption.map { option =>
            val game: JsObject = option.get
            val newGameState: JsValue = (game \ "gameState").get
            Ok(newGameState)
        }
     }

}
