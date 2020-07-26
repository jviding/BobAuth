'use strict'

export default class Urls {

    constructor() {
        const BASE_URL = 'http://admin.rpylkkanen.com/api'

        this.login = BASE_URL + '/login'
        this.logout = BASE_URL + '/logout'
        this.profile = BASE_URL + '/profile'

        this.users = BASE_URL + '/users'
    }

}
