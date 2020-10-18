'use strict'
import URLs from './urls.jsx'
import Requests from './requests.jsx'

export default class BobAPI {

    constructor() {
        this.URLs = new URLs()
        this.Requests = new Requests()

        this.login = this.login.bind(this)
        this.logout = this.logout.bind(this)
        this.signup = this.signup.bind(this)
        this.getProfile = this.getProfile.bind(this)
        this.updateProfile = this.updateProfile.bind(this)

        this.loadGame = this.loadGame.bind(this)
        this.saveGame = this.saveGame.bind(this)
    }

    loadGame() {
        const urlParams = { gameName: 'bobz' }
        return this.Requests.GET(this.URLs.loadGame, urlParams)
    }

    saveGame(uname) {
        const BODY = { test1: uname }
        return this.Requests.PUT(this.URLs.saveGame + '?gameName=bobz', BODY)
    }

    login(username, password) {
        const BODY = { username: username, password: password }
        return this.Requests.POST(this.URLs.login, BODY)
    }

    logout() {
        return this.Requests.POST(this.URLs.logout, {})
    }

    signup(username, password, email) {
        const BODY = { username: username, password: password, email: email }
        return this.Requests.POST(this.URLs.signup, BODY)
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

}
