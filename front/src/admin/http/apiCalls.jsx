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
