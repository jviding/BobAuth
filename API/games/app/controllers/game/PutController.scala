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

class PutController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))


    def updateGame(gameID: String, newName: String) = Action.async {
        (isValidGameID(gameID), isValidGameName(newName)) match {
            case (false, _) => Future { BadRequest("Invalid gameID") }
            case (_, false) => Future { BadRequest("Invalid game name") }
            case (true, true) => {
                //val selector = BSONDocument("_id")
                //val update = BSONDocument("$set" -> BSONDocument("name" -> newName))
                //val projection = ??

                Future { Ok("Success") }
            }
        }
/*
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


        Future { Ok("") }*/
    }
}
