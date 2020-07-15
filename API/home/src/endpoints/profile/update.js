const HTTP = require('http')

module.exports = (req) => {
    return new Promise((resolve, reject) => {

        const POST_DATA = JSON.stringify({
            newEmail: req.body.newEmail,
            newPassword: req.body.newPassword,
            password: req.body.password
        })

        const REQ_OPTIONS = {
            hostname: (require('../../urls.js')).iam.host.hostname,
            port: (require('../../urls.js')).iam.host.port,
            path: (require('../../urls.js')).iam.paths.profile,
            method: 'PUT',
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
                res.setEncoding('utf8')
                let body = ''
                res.on('data', (chunk) => body += chunk)
                res.on('end', () => {
                    let user = JSON.parse(body)
                    let responseCookies = !!res.headers['set-cookie'] ? {
                        'set-cookie': res.headers['set-cookie']
                    } : false
                    let responseBody = {
                        username: user.username,
                        email: user.email,
                        isAdmin: user.isAdmin
                    }
                    resolve([responseCookies, responseBody])
                })
            } else {
                reject()
            }
        })

        REQ.write(POST_DATA)
        REQ.end()
    })
}
