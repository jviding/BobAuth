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


class GamesController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def getGames() = Action.async {
        gamesCollection.flatMap(
            _.find(BSONDocument())
            .cursor[JsObject](ReadPreference.primary)
            .collect[List](-1, Cursor.FailOnError[List[JsObject]]())
        )
        .map(
            _.map(formatGame(_))
        )
        .map(Json.toJson(_))
        .map(Ok(_))
    }

    private def formatGame(game: JsObject): JsObject = {
        val id: JsResult[String] = (game \ "_id" \ "$oid").validate[String]
        val name: JsResult[String] = (game \ "name").validate[String]
        val mainFile: JsResult[String] = (game \ "mainFile").validate[String]
        val resourceFiles: JsResult[Seq[String]] = (game \ "resourceFiles").validate[Seq[String]]
        Json.obj(
            "id" -> JsString(id.getOrElse("")),
            "name" -> JsString(name.getOrElse("")),
            "mainFile" -> JsString(mainFile.getOrElse("")),
            "resourceFiles" -> JsArray(resourceFiles.getOrElse(Seq.empty[String]).map(JsString(_)))
        )
    }
}
