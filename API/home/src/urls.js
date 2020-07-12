module.exports = {
    iam: {
        host: {
            hostname: 'api-iam',
            port: 8000
        },
        paths: {
            login: '/login',
            logout: '/logout',
            profile: '/profile',
            signup: '/signup'
        }
    },
    games: {
        host: {
            hostname: 'api-games',
            port: 9000
        },
        paths: {
            load: '/load',
            save: '/save'
        }
    }
}
