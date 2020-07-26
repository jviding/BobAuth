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

        this.getUsers = this.getUsers.bind(this)
    }

    login(username, password) {
        const BODY = { username: username, password: password }
        return this.Requests.POST(this.URLs.login, BODY)
            .then((response) => {
                if (response.status === 200) {
                    return Promise.resolve(true)
                } else {
                    return Promise.reject('Login failed!')
                }
            })
    }

    logout() {
        return this.Requests.POST(this.URLs.logout, {})
            .then((response) => {
                if (response.status === 200) {
                    return Promise.resolve(true)
                } else {
                    return Promise.reject('Logout failed!')
                }
            })
    }

    getProfile() {
        return this.Requests.GET(this.URLs.profile)
            .then((response) => {
                if (response.status === 200) {
                    return Promise.resolve(JSON.parse(response.response))
                } else {
                    return Promise.reject('Failed to load profile!')
                }
            })
    }

    updateProfile(newEmail, newPassword, password) {
        const BODY = { newEmail: newEmail, newPassword: newPassword, password: password }
        return this.Requests.PUT(this.URLs.profile, BODY)
            .then((response) => {
                if (response.status === 200) {
                    return Promise.resolve(JSON.parse(response.response))
                } else {
                    return Promise.reject('Failed to update profile!')
                }
            })
    }

    getUsers() {
        return this.Requests.GET(this.URLs.users)
            .then((response) => {
                if (response.status === 200) {
                    return Promise.resolve(JSON.parse(response.response))
                } else {
                    return Promise.reject('Failed to update profile!')
                }
            })
    }
}
