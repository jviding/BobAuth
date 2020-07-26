"use strict";

const sendRequest = require('../lib/sendRequest.js')

module.exports = (req) => {

    const REQ_OPTIONS = {
        method: 'POST',
        host: 'iam',
        endpoint: 'signup',
        urlParams: false,
        payload: {
            username: req.body.username,
            password: req.body.password,
            email: req.body.email
        }
    }

    return sendRequest(req, REQ_OPTIONS)
        .then((res) => {
            if (res.statusCode === 200) {
                const COOKIES = res.headers['set-cookie']
                return Promise.resolve(COOKIES)
            } else {
                return Promise.reject()
            }
        })
}