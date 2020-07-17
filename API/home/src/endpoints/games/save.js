"use strict";

const isAuthenticated = require('../../lib/isAuthenticated.js')
const sendRequest = require('../../lib/sendRequest.js')

module.exports = (req) => {

    const REQ_OPTIONS = {
        method: 'PUT',
        host: 'games',
        endpoint: 'save',
        urlParams: {
            gameName: req.query.gameName
        },
        payload: req.body
    }

    return isAuthenticated(req)
        .then((res) => {
            REQ_OPTIONS.urlParams.userID = res.userID
            return sendRequest(req, REQ_OPTIONS)
        })
        .then((res) => {
            if (res.statusCode === 200) {
                return Promise.resolve(JSON.parse(res.body))
            } else {
                return Promise.reject()
            }
        })
}
