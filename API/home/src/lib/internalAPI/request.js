"use strict";

const HTTP = require('http')

module.exports = (reqOptions, body = false) => {

    if (!!body) {
        body = JSON.stringify(body)
        reqOptions.headers['Content-Type'] = 'application/json'
        reqOptions.headers['Content-Length'] = Buffer.byteLength(body)
    }

    return new Promise((resolve, reject) => {

        const REQ = HTTP.request(reqOptions, (res) => {
            res.setEncoding('utf8')
            let responseBody = ''
            res.on('data', (chunk) => responseBody += chunk)
            res.on('end', () => {
                resolve({
                    statusCode: res.statusCode,
                    headers: res.headers,
                    body: responseBody
                })
            })
        })

        REQ.on('error', reject)

        if (!!body) {
            REQ.write(body)
        }

        REQ.end()
    })
}
