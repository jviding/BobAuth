"use strict";

module.exports = {
    iam: {
        hostname: 'api-iam',
        port: 8000,
        paths: {
            login: '/login',
            logout: '/logout',
            profile: '/profile',
            signup: '/signup'
        }
    },
    games: {
        hostname: 'api-games',
        port: 9000,
        paths: {
            load: '/load',
            save: '/save'
        }
    }
}
