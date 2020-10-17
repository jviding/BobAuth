"use strict";

const URLs = require('./urls.js')
const sendRequest = require('./request.js')

module.exports = {

    signup: (payload) => {
        const REQ_OPTIONS = {
            method: 'POST',
            hostname: URLs.iam.hostname,
            port: URLs.iam.port,
            path: URLs.iam.endpoints.signup,
            headers: {}
        }
        return sendRequest(REQ_OPTIONS, payload)
    },

    login: (payload) => {
        const REQ_OPTIONS = {
            method: 'POST',
            hostname: URLs.iam.hostname,
            port: URLs.iam.port,
            path: URLs.iam.endpoints.login,
            headers: {}
        }
        return sendRequest(REQ_OPTIONS, payload)
    },

    logout: (sessionid) => {
        const REQ_OPTIONS = {
            method: 'POST',
            hostname: URLs.iam.hostname,
            port: URLs.iam.port,
            path: URLs.iam.endpoints.logout,
            headers: {
                Cookie: 'sessionid=' + sessionid
            }
        }
        return sendRequest(REQ_OPTIONS)
    },

    getProfile: (sessionid) => {
        const REQ_OPTIONS = {
            method: 'GET',
            hostname: URLs.iam.hostname,
            port: URLs.iam.port,
            path: URLs.iam.endpoints.profile,
            headers: {
                Cookie: 'sessionid=' + sessionid
            }
        }
        return sendRequest(REQ_OPTIONS)
    },

    updateProfile: (sessionid, payload) => {
        const REQ_OPTIONS = {
            method: 'PUT',
            hostname: URLs.iam.hostname,
            port: URLs.iam.port,
            path: URLs.iam.endpoints.profile,
            headers: {
                Cookie: 'sessionid=' + sessionid
            }
        }
        return sendRequest(REQ_OPTIONS, payload)
    },

    getGames: () => {
        const REQ_OPTIONS = {
            method: 'GET',
            hostname: URLs.games.hostname,
            port: URLs.games.port,
            path: URLs.games.endpoints.games,
            headers: {}
        }
        return sendRequest(REQ_OPTIONS)
    },

    getGame: (gameID) => {
        const PATH = URLs.games.endpoints.game + '/' + gameID
        const REQ_OPTIONS = {
            method: 'GET',
            hostname: URLs.games.hostname,
            port: URLs.games.port,
            path: PATH,
            headers: {}
        }
        return sendRequest(REQ_OPTIONS)
    },

    loadGame: () => {
        // Maybe needs to be hosted under different app?
        // Otherwise Host header checks need to be set to every endpoint...
        // Load shouldn't expose gameToken to other game domains!
        return Promise.reject()
    },

    saveGame: (gameToken, payload) => {
        return Promise.reject()
    }

}
