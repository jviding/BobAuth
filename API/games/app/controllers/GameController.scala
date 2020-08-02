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

import scala.util.Try

class GameController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def createGame() = Action.async(parse.json) { request: Request[JsValue] =>
        
        val gameName: String = (request.body.as[JsObject] \ "gameName").validate[String].getOrElse("Undefined")

        val gameSelector = BSONDocument("name" -> gameName)
        val gamesWithGivenName: Future[Option[BSONDocument]] = gamesCollection.flatMap(
            _.find(gameSelector).one[BSONDocument]
        )

        val newGame = BSONDocument(
            "name" -> gameName, 
            "mainFile" -> "",
            "resourceFiles" -> BSONArray()
        )
        def insertNewGame(newGame: BSONDocument): Future[Boolean] = gamesCollection.flatMap(
            _.insert.one(newGame).map(_.ok)
        )

        gamesWithGivenName.flatMap {
            case Some(game) => Future { Conflict("Game with the given name already exists") }
            case None => insertNewGame(newGame).map {
                case true => Ok("")
                case false => InternalServerError("Something went wrong")
            }
        }
    }

    //def update(userID: String, gameName: String)

    def deleteGame() = Action.async(parse.json) { request: Request[JsValue] =>

        val gameID: String = (request.body.as[JsObject] \ "gameID").validate[String].getOrElse("")

        val id: Try[BSONObjectID] = BSONObjectID.parse(gameID)

        println("ID:" + gameID)
        println(BSONObjectID.parse(gameID))

        val gameSelector = BSONDocument("_id" -> id.get)
        val removed: Future[List[JsObject]] = gamesCollection.flatMap(
            _.find(gameSelector)
            .cursor[JsObject](ReadPreference.primary)
            .collect[List](1, Cursor.FailOnError[List[JsObject]]())
        )

        removed.map { g =>
            println("FOUND:")
            println(g)
            g.map(println(_))
        }

        //removed.map(println(_))

        Future.successful(Ok("hello world"))
    }

}
