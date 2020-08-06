'use strict'

export default class Urls {

    constructor() {
        const BASE_URL = 'http://admin.rpylkkanen.com/api'

        this.login = BASE_URL + '/login'
        this.logout = BASE_URL + '/logout'
        this.profile = BASE_URL + '/profile'

        this.user = BASE_URL + '/user'
        this.users = BASE_URL + '/users'
        
        this.game = BASE_URL + '/game'
        this.gameFile = BASE_URL + '/gamefile'
        this.games = BASE_URL + '/games'
    }

}
