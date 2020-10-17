"use strict";

const sendRequest = require('../lib/sendRequest.js')

module.exports = (req, res) => {

    const REQ_OPTIONS = {
        method: 'POST',
        host: 'iam',
        endpoint: 'logout',
        urlParams: false,
        payload: {}
    }

    return sendRequest(req, REQ_OPTIONS)
        .then((response) => {
            if (response.statusCode === 200) {
                const COOKIES = response.headers['set-cookie']
                res.set({ 'set-cookie': COOKIES })
                res.status(200).json({})
            } else {
                res.sendStatus(404).end()
            }
        })
}
