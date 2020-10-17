"use strict";

const API = require('../lib/internalAPI/API.js')

module.exports = (req, res) => {

    const PAYLOAD = {
        username: req.body.username,
        password: req.body.password,
        email: req.body.email
    }

    API.signup(PAYLOAD)
    .then((response) => {
        if (response.statusCode === 200) {
            const COOKIES = response.headers['set-cookie']
            res.set({ 'set-cookie': COOKIES })
            res.status(200).json({})
        } else {
            res.sendStatus(403).end()
        }
    })
    .catch(() => res.sendStatus(500).end())
}
