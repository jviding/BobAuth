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
        this.createGame = this.createGame.bind(this)
        this.updateGameName = this.updateGameName.bind(this)
        this.deleteGame = this.deleteGame.bind(this)

        this.uploadGameMainFile = this.uploadGameMainFile.bind(this)
        this.uploadGameResourceFile = this.uploadGameResourceFile.bind(this)
        this.deleteGameResourceFiles = this.deleteGameResourceFiles.bind(this)

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

    getProfile() {
        return this.Requests.GET(this.URLs.profile)
    }

    updateProfile(newEmail, newPassword, password) {
        const BODY = { newEmail: newEmail, newPassword: newPassword, password: password }
        return this.Requests.PUT(this.URLs.profile, BODY)
    }

    getGames() {
        return this.Requests.GET(this.URLs.games)
    }

    createGame(gameName) {
        const BODY = { gameName: gameName }
        return this.Requests.POST(this.URLs.game, BODY)
    }

    updateGameName(gameID, newGameName) {
        const BODY = { gameID: gameID, newGameName: newGameName }
        return this.Requests.PUT(this.URLs.game, BODY)
    }

    deleteGame(gameID) {
        const BODY = { gameID: gameID }
        return this.Requests.DELETE(this.URLs.game, BODY)
    }

    uploadGameMainFile(gameID, newMainFile) {
        return Promise.reject('Uploading file not implemeted!')
    }

    uploadGameResourceFile(gameID, newResourceFile) {
        return Promise.reject('Uploading file not implemeted!')
    }

    deleteGameResourceFiles(filenames) {
        const BODY = { filenames: filenames }
        return this.Requests.DELETE(this.URLs.gameFile, BODY)
    }

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
