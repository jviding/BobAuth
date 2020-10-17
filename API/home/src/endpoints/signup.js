"use strict";

const sendRequest = require('../lib/sendRequest.js')

module.exports = (req, res) => {

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
        .then((response) => {
            if (response.statusCode === 200) {
                const COOKIES = response.headers['set-cookie']
                res.set({ 'set-cookie': COOKIES })
                res.status(200).json({})
            } else {
                res.sendStatus(403).end()
            }
        })
}
