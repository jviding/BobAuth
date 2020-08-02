package models.game

case class Game(
  name: String,
  entryFile: String,
  resourceFiles: List[String])


object JsonFormats {
  import play.api.libs.json.Json

  implicit val gameFormat = Json.format[Game]
}