const HTTP = require('http')

module.exports = (req) => {
    return new Promise((resolve, reject) => {

        const REQ_OPTIONS = {
            hostname: (require('../../urls.js')).iam.host.hostname,
            port: (require('../../urls.js')).iam.host.port,
            path: (require('../../urls.js')).iam.paths.profile,
            method: 'GET',
            headers: {}
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
                    resolve({
                        username: user.username,
                        email: user.email,
                        isAdmin: user.isAdmin
                    })
                })
            } else {
                reject()
            }
        })

        REQ.end()
    })
}
