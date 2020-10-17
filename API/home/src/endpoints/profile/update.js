"use strict";

const sendRequest = require('../../lib/sendRequest.js')

module.exports = (req, res) => {

    const REQ_OPTIONS = {
        method: 'PUT',
        host: 'iam',
        endpoint: 'profile',
        urlParams: false,
        payload: {
            newEmail: req.body.newEmail,
            newPassword: req.body.newPassword,
            password: req.body.password
        }
    }

    return sendRequest(req, REQ_OPTIONS)
        .then((response) => {
            if (response.statusCode === 200) {
                if (!!response.headers['set-cookie']) {
                    const COOKIES = response.headers['set-cookie']
                    res.set({ 'set-cookie': COOKIES })
                }
                const USER = JSON.parse(response.body)
                res.status(200).json({
                    username: USER.username,
                    email: USER.email,
                    isAdmin: USER.isAdmin
                })
            } else {
                res.sendStatus(403).end()
            }
        })
}
