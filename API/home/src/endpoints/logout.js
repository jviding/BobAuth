"use strict";

const API = require('../lib/internalAPI/API.js')

module.exports = (req, res) => {

    const COOKIE = (!!req.cookies.sessionid ? req.cookies.sessionid : '')

    API.logout(COOKIE)
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
