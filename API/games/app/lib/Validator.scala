package lib

object Validator {

    def isValidGameID(gameID: String): Boolean = gameID.length > 20 && gameID.length() < 30 && (gameID matches "^[a-zA-Z0-9]+$")

    // TODO: Validation
    def isValidGameName(gameName: String): Boolean = true //gameName matches "^[0-9]$"

}