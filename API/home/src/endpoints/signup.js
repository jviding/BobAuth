const HTTP = require('http')

module.exports = (req) => {
    return new Promise((resolve, reject) => {

        const POST_DATA = JSON.stringify({
            username: req.body.username,
            password: req.body.password,
            email: req.body.email
        })

        const REQ_OPTIONS = {
            hostname: (require('../urls.js')).iam.host.hostname,
            port: (require('../urls.js')).iam.host.port,
            path: (require('../urls.js')).iam.paths.signup,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(POST_DATA)
            }
        }

        const REQ = HTTP.request(REQ_OPTIONS, (res) => {
            if (res.statusCode === 200) {
                resolve({ 'set-cookie': res.headers['set-cookie'] })
            } else {
                reject()
            }
        })

        REQ.write(POST_DATA)
        REQ.end()
    })
}
