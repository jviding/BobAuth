"use strict";

const sendRequest = require('../../lib/sendRequest.js')

module.exports = (req) => {

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
        .then((res) => {
            if (res.statusCode === 200) {
                const COOKIES = !!res.headers['set-cookie'] ? res.headers['set-cookie'] : false
                const USER = JSON.parse(res.body)
                const BODY = {
                    username: USER.username,
                    email: USER.email,
                    isAdmin: USER.isAdmin
                }
                return Promise.resolve([COOKIES, BODY])
            } else {
                return Promise.reject()
            }
        })
}
