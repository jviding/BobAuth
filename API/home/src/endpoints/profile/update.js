"use strict";

const API = require('../../lib/internalAPI/API.js')

module.exports = (req, res) => {

    const COOKIE = (!!req.cookies.sessionid ? req.cookies.sessionid : '')

    const PAYLOAD = {
        newEmail: req.body.newEmail,
        newPassword: req.body.newPassword,
        password: req.body.password
    }

    API.updateProfile(COOKIE, PAYLOAD)
    .then((response) => {
        if (response.statusCode === 200) {
            if (!!response.headers['set-cookie']) {
                const COOKIES = response.headers['set-cookie']
                res.set({ 'set-cookie': COOKIES })
            }
            res.status(200).json({})
        } else {
            res.sendStatus(403).end()
        }
    })
    .catch((err) => res.sendStatus(500).end())
}
