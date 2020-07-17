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
