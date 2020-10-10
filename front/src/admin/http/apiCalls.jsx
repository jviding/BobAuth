'use strict'
import URLs from './urls.jsx'
import Requests from './requests.jsx'

export default class BobAPI {

    constructor() {
        this.URLs = new URLs()
        this.Requests = new Requests()

        this.login = this.login.bind(this)
        this.logout = this.logout.bind(this)
        this.getProfile = this.getProfile.bind(this)
        this.updateProfile = this.updateProfile.bind(this)

        this.getGames = this.getGames.bind(this)
        this.getGame = this.getGame.bind(this)
        this.createGame = this.createGame.bind(this)
        this.updateGame = this.updateGame.bind(this)
        this.deleteGame = this.deleteGame.bind(this)
        this.uploadGameFile = this.uploadGameFile.bind(this)

        this.getUsers = this.getUsers.bind(this)
        this.updateUser = this.updateUser.bind(this)
        this.deleteUser = this.deleteUser.bind(this)
    }

    login(username, password) {
        const BODY = { username: username, password: password }
        return this.Requests.POST(this.URLs.login, BODY)
    }

    logout() {
        return this.Requests.POST(this.URLs.logout, {})
    }

    // PROFILE

    getProfile() {
        return this.Requests.GET(this.URLs.profile)
    }

    updateProfile(newEmail, newPassword, password) {
        const BODY = { newEmail: newEmail, newPassword: newPassword, password: password }
        return this.Requests.PUT(this.URLs.profile, BODY)
    }

    // GAMES

    getGames() {
        return this.Requests.GET(this.URLs.games)
    }

    getGame(gameID) {
        return this.Requests.GET(this.URLs.game + "/" + gameID)
    }

    createGame(gameName) {
        const BODY = { gameName: gameName }
        return this.Requests.POST(this.URLs.game, BODY)
    }

    updateGame(gameID, newName) {
        const BODY = { gameID: gameID, newName: newName }
        return this.Requests.PUT(this.URLs.game, BODY)
    }

    deleteGame(gameID) {
        const BODY = { gameID: gameID }
        return this.Requests.DELETE(this.URLs.game, BODY)
    }

    // FILES

    uploadGameFile(gameID, type, file) {
        const fd = new FormData()
        fd.append('gameID', gameID)
        fd.append('type', type)
        fd.append('file', file)
        return this.Requests.POSTFile(this.URLs.gameFile, fd)
    }

    deleteGameFile(gameID, type, filename) {
        const BODY = { gameID: gameID, type: type, filename: filename }
        return this.Requests.DELETE(this.URLs.gameFile, BODY)
    }

    // USERS

    getUsers() {
        return this.Requests.GET(this.URLs.users)
    }

    updateUser(userID, email, isAdmin) {
        const BODY = { userID: userID, email: email, isAdmin: isAdmin }
        return this.Requests.PUT(this.URLs.user, BODY)
    }

    deleteUser(userID) {
        const BODY = { userID: userID }
        return this.Requests.DELETE(this.URLs.user, BODY)
    }
}
