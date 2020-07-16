"use strict";

const sendRequest = require('./sendRequest.js')

module.exports = (req) => {

    const REQ_OPTIONS = {
        method: 'GET',
        host: 'iam',
        endpoint: 'profile',
        urlParams: false,
        payload: false
    }

    return sendRequest(req, REQ_OPTIONS)
        .then((res) => {
            if (res.statusCode === 200) {
                return Promise.resolve(JSON.parse(res.body))
            } else {
                return Promise.reject()
            }
        })
}
