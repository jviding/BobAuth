"use strict";

const isAuthenticated = require('../../lib/isAuthenticated.js')

module.exports = (req) => {
    return isAuthenticated(req)
        .then((res) => {
            return {
                username: res.username,
                email: res.email,
                isAdmin: res.isAdmin
            }
        })
}
