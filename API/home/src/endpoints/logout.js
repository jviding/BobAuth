const HTTP = require('http')

module.exports = (req) => {
    return new Promise((resolve, reject) => {

        const POST_DATA = JSON.stringify({})

        const REQ_OPTIONS = {
            hostname: (require('../urls.js')).iam.host.hostname,
            port: (require('../urls.js')).iam.host.port,
            path: (require('../urls.js')).iam.paths.logout,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(POST_DATA)
            }
        }

        if (!!req.cookies.sessionid) {
            REQ_OPTIONS.headers['Cookie'] = 'sessionid=' + req.cookies.sessionid
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
