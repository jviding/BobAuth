"use strict";

const isAuthenticated = require('../../lib/isAuthenticated.js')

module.exports = (req, res) => {
    return isAuthenticated(req)
        .then((response) => {
            res.status(200).json({
                username: response.username,
                email: response.email,
                isAdmin: response.isAdmin
            })
        })
        .catch(() => res.sendStatus(403).end())
}
