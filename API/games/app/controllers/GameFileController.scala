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

import scala.util.{ Try, Success, Failure }

class GameFileController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def uploadFile() = Action(parse.multipartFormData) { request =>

        // Check type
        // Store file (sha filename)
        // Update game model

        println(request)

/*        request.body.map {
            println(_)
        }*/
/*
        request.body
            .file("picture")
            .map { picture =>
            // only get the last part of the filename
            // otherwise someone can send a path like ../../home/foo/bar.txt to write to other files on the system
            val filename    = Paths.get(picture.filename).getFileName
            val fileSize    = picture.fileSize
            val contentType = picture.contentType

            picture.ref.copyTo(Paths.get(s"/tmp/picture/$filename"), replace = true)
            Ok("File uploaded")
            }
            .getOrElse {
            Redirect(routes.HomeController.index).flashing("error" -> "Missing file")
            }*/


        Ok("")
    }
}
