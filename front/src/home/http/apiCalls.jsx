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
        return new Promise((resolve, reject) => {
            const urlParams = { gameName: 'bobz' }
            this.Requests.GET(this.URLs.loadGame, urlParams)
            .then((response) => {
                if (response.status === 200) {
                    resolve(JSON.parse(response.response))
                } else {
                    reject('Failed to load game!')
                }
            })
        })
    }

    saveGame(uname) {
        return new Promise((resolve, reject) => {
            const BODY = { test1: uname }
            this.Requests.PUT(this.URLs.saveGame + '?gameName=bobz', BODY)
            .then((response) => {
                if (response.status === 200) {
                    resolve(JSON.parse(response.response))
                } else {
                    reject('Failed to save game!')
                }
            })
        })
    }

    login(username, password) {
        return new Promise((resolve, reject) => {
            const BODY = { username: username, password: password }
            this.Requests.POST(this.URLs.login, BODY)
            .then((response) => {
                if (response.status === 200) {
                    resolve(true)
                } else {
                    reject('Login failed!')
                }
            })
        })
    }

    logout() {
        return new Promise((resolve, reject) => {
            this.Requests.POST(this.URLs.logout, {})
            .then((response) => {
                if (response.status === 200) {
                    resolve(true)
                } else {
                    reject('Logout failed!')
                }
            })
        })
    }

    signup(username, password, email) {
        return new Promise((resolve, reject) => {
            const BODY = { username: username, password: password, email: email }
            this.Requests.POST(this.URLs.signup, BODY)
            .then((response) => {
                if (response.status === 200) {
                    resolve(true)
                } else {
                    reject('Signup failed!')
                }
            })
        })
    }

    getProfile() {
        return new Promise((resolve, reject) => {
            this.Requests.GET(this.URLs.profile)
            .then((response) => {
                if (response.status === 200) {
                    resolve(JSON.parse(response.response))
                } else {
                    reject('Failed to load profile!')
                }
            })
        })
    }

    updateProfile(newEmail, newPassword, password) {
        return new Promise((resolve, reject) => {
            const BODY = { newEmail: newEmail, newPassword: newPassword, password: password }
            this.Requests.PUT(this.URLs.profile, BODY)
            .then((response) => {
                if (response.status === 200) {
                    resolve(JSON.parse(response.response))
                } else {
                    reject('Failed to update profile!')
                }
            })
        })
    }
}
