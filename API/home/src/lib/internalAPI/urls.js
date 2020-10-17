"use strict";

module.exports = {
    iam: {
        hostname: 'api-iam',
        port: 8000,
        endpoints: {
            login: '/login',
            logout: '/logout',
            profile: '/profile',
            signup: '/signup'
        }
    },
    games: {
        hostname: 'api-games',
        port: 9000,
        endpoints: {
            games: '/games',
            game: '/game',
            load: '/load',
            save: '/save'
        }
    }
}
