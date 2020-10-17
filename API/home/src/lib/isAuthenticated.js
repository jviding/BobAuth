"use strict";

const API = require('./internalAPI/API.js')

module.exports = (req, res, next) => {

    const COOKIE = (!!req.cookies.sessionid ? req.cookies.sessionid : '')

    API.getProfile(COOKIE)
    .then((response) => {
        if (response.statusCode === 200) {
            req.user = JSON.parse(response.body)
            next()
        } else {
            res.sendStatus(403).end()
        }
    })
}
