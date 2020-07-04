import URLs from './urls.jsx'
import Requests from './requests.jsx'

export default class BobAPI {

    constructor() {
        this.URLs = new URLs()
        this.Requests = new Requests()

        this.login = this.login.bind(this)
        this.logout = this.logout.bind(this)
        this.profile = this.profile.bind(this)
        this.signup = this.signup.bind(this)
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

    profile() {
        return new Promise((resolve, reject) => {
            this.Requests.POST(this.URLs.profile, {})
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
}
