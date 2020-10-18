'use strict'

export default class Urls {

    constructor() {
        const BASE_URL = 'http://rpylkkanen.com/api'

        this.login = BASE_URL + '/login'
        this.logout = BASE_URL + '/logout'
        this.profile = BASE_URL + '/profile'
        this.signup = BASE_URL + '/signup'

        this.games = BASE_URL + '/games'

        this.loadGame = BASE_URL + '/games/load'
        this.saveGame = BASE_URL + '/games/save'
    }

}
