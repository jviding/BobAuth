package lib

object Validator {

    def isValidGameID(gameID: String): Boolean = gameID matches "^[a-zA-Z0-9]{20,30}$"

    def isValidGameName(gameName: String): Boolean = gameName matches "^[a-zA-Z0-9 -]{2,32}$"

}