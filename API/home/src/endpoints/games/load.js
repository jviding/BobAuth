"use strict";

const isAuthenticated = require('../../lib/isAuthenticated.js')
const sendRequest = require('../../lib/sendRequest.js')

module.exports = (req) => {

    const REQ_OPTIONS = {
        method: 'GET',
        host: 'games',
        endpoint: 'load',
        urlParams: {
            gameName: req.query.gameName
        },
        payload: false
    }

    return isAuthenticated(req)
        .then((res) => {
            REQ_OPTIONS.urlParams.userID = res.userID
            return sendRequest(req, REQ_OPTIONS)
        })
        .then((res) => {
            console.log(res)
            return Promise.resolve(JSON.parse(res.body))
        })
}
