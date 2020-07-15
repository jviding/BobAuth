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
    }

    login(username, password) {
        return new Promise((resolve, reject) => {
            const BODY = { username: username, password: password }
            this.Requests.POST(this.URLs.login, BODY)
            .then((response) => {
                if (response.status === 200) {
                    resolve(true)
                } else {
                    reject('Incorrect credentials!')
                }
            })
            .catch((e) => { reject(e) })
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
            .catch((e) => { reject(e) })
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
            .catch((e) => { reject(e) })
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
            .catch((e) => { reject(e) })
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
            .catch((e) => { reject(e) })
        })
    }
}
