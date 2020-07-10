package models

case class Game(
    userID: String,
    gameName: String,
    gameState: String
)

object JsonFormats {
    import play.api.libs.json.Json

    implicit val gameFormat = Json.format[Game]
}