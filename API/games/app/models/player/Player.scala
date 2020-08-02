package models.player

case class Player(
  userID: Int,
  savedGames: List[SavedGame])

case class SavedGame(
  gameName: String,
  gameState: String)

object JsonFormats {
  import play.api.libs.json.Json

  implicit val savedGameFormat = Json.format[SavedGame]
  implicit val playerFormat = Json.format[Player]
}