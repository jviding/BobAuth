package controllers

import javax.inject._

import scala.concurrent.{ ExecutionContext, Future }

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Json

import play.modules.reactivemongo.{
    MongoController,
    ReactiveMongoApi,
    ReactiveMongoComponents
}

import reactivemongo.api.bson._
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.commands.bson.BSONFindAndModifyCommand.FindAndModifyResult

import reactivemongo.api.Cursor
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection._

import models._
import models.JsonFormats._


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
        
        gameOption.map(option => Ok(option.get))
    }

    def save(userID: String, gameName: String) = Action.async {
        val selector = BSONDocument("userID" -> userID, "gameName" -> gameName)
        val update = BSONDocument("$set" -> BSONDocument("gameState" -> "qweqwe"))
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

        gameOption.map(option => Ok(option.get))
     }

}
