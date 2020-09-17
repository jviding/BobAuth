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

import reactivemongo.api.BSONSerializationPack
import reactivemongo.api.gridfs.{ DefaultFileToSave, GridFS }
import reactivemongo.api.gridfs.Implicits._
import reactivemongo.bson.BSONValue

import scala.util.{ Try, Success, Failure }

class GameFileController @Inject() (
    controllerComponents: ControllerComponents,
    val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(controllerComponents) with MongoController with ReactiveMongoComponents {

    implicit def ec: ExecutionContext = controllerComponents.executionContext

    def gamesCollection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("games"))

    def uploadFile() = Action(parse.multipartFormData) { request =>

        //val res = 
        for {
            id <- request.body.dataParts.get("gameID")
            fileType <- request.body.dataParts.get("fileType")
            file <- request.body.file("file")
        } yield {
            println(id)
            println(fileType)
            println(file)


            // Store in resource file name in game
            // Store gamefile
        }


        Ok("")
    }

    def getFile() = Action {
        Ok("")
    }
}

/*
FIND FILE:

import scala.concurrent.{ ExecutionContext, Future }

import reactivemongo.api.BSONSerializationPack
import reactivemongo.api.gridfs.{ GridFS, ReadFile }
import reactivemongo.api.gridfs.Implicits._
import reactivemongo.bson.{ BSONDocument, BSONValue }

def gridfsByFilename(
  gridfs: GridFS[BSONSerializationPack.type],
  filename: String
)(implicit ec: ExecutionContext): Future[ReadFile[BSONSerializationPack.type, BSONValue]] = {
  def cursor = gridfs.find(BSONDocument("filename" -> filename))
  cursor.head
}
*/

/*
DELETE FILE:

import scala.concurrent.{ ExecutionContext, Future }

import reactivemongo.api.BSONSerializationPack
import reactivemongo.api.gridfs.GridFS //, ReadFile }
//import reactivemongo.api.gridfs.Implicits._
import reactivemongo.bson.BSONValue

def removeFrom(
  gridfs: GridFS[BSONSerializationPack.type],
  id: BSONValue // see ReadFile.id
)(implicit ec: ExecutionContext): Future[Unit] =
  gridfs.remove(id).map(_ => {})
*/
