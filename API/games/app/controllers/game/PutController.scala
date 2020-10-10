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

    private def update(gameID: String, newName: String): Future[Boolean] = {
        BSONObjectID.parse(gameID) match {
            case Failure(e) => Future { false }
            case Success(id) => {
                val selector = BSONDocument("_id" -> id)
                val update = BSONDocument("$set" -> BSONDocument("name" -> newName))
                gamesCollection.flatMap(
                    _.findAndUpdate(selector, update)
                    .map(_.result[BSONDocument])
                    .map {
                        case Some(item) => true
                        case None => false
                    }
                )
            }
        }
    }


    def updateGame(gameID: String, newName: String) = Action.async {
        (isValidGameID(gameID), isValidGameName(newName)) match {
            case (false, _) => Future { BadRequest("Invalid gameID") }
            case (_, false) => Future { BadRequest("Invalid game name") }
            case (true, true) => update(gameID, newName).map {
                    case false => BadRequest("Something went wrong")
                    case true => Ok("Success")
            }
        }
    }
}
