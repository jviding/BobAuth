"use strict";

const HTTP = require('http')
const URLs = require('../urls.js')

module.exports = (req, options) => {

    /* OPTIONS OBJECT
    {
        method: 'GET|POST|PUT|DELETE'
        host: '...',
        endpoint: '...',
        urlParams: {...} | false,
        payload: {...} | false
    }
    */

    const REQ_OPTIONS = {
        hostname: URLs[options.host].hostname,
        port: URLs[options.host].port,
        path: URLs[options.host].paths[options.endpoint],
        method: options.method,
        headers: {}
    }

    if (!!options.urlParams) {
        const QUERY_STRING = Object.entries(options.urlParams).map(([k,v]) => { return k + '=' + v }).join('&')
        REQ_OPTIONS.path += '?' + QUERY_STRING
    }

    if (!!options.payload) {
        options.payload = JSON.stringify(options.payload)
        REQ_OPTIONS.headers['Content-Type'] = 'application/json'
        REQ_OPTIONS.headers['Content-Length'] = Buffer.byteLength(options.payload)
    }

    if (!!req.cookies.sessionid) {
        REQ_OPTIONS.headers['Cookie'] = 'sessionid=' + req.cookies.sessionid
    }


    return new Promise((resolve, reject) => {

        const REQ = HTTP.request(REQ_OPTIONS, (res) => {
            res.setEncoding('utf8')
            let body = ''
            res.on('data', (chunk) => body += chunk)
            res.on('end', () => {
                resolve({
                    statusCode: res.statusCode,
                    headers: res.headers,
                    body: body
                })
            })
        })

        REQ.on('error', reject)

        if (!!options.payload) {
            REQ.write(options.payload)
        }

        REQ.end()
    })
}
