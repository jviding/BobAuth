'use strict'

export default class Requests {

    constructor() {
        this.makeRequest = this.makeRequest.bind(this)
        this.GET = this.GET.bind(this)
        this.POST = this.POST.bind(this)
        this.PUT = this.PUT.bind(this)
        this.DELETE = this.DELETE.bind(this)
    }

    makeRequest(method, url, body) {
        return new Promise((resolve, reject) => {
            var req = new XMLHttpRequest()
            req.onload = () => {
                if (req.status === 200) {
                    resolve(JSON.parse(req.responseText))
                } else {
                    reject(req.responseText)
                }
            }
            req.onerror = () => {
                reject("Request failed due to a network failure!")
            }
            req.open(method, url)
            if (['POST', 'PUT', 'DELETE'].includes(method)) {
                req.setRequestHeader('Content-Type', 'application/json')
            }
            req.send(body !== null ? JSON.stringify(body) : null)
        })
    }

    GET(url, params = false) {
        let urlParams = ''
        if (!!params) {
            // TODO: UrlParams as Base64
            urlParams = '?' + Object.entries(params).map(([k, v]) => { return k + '=' + v }).join('&')
        }
        return this.makeRequest('GET', url + urlParams, null)
    }

    POST(url, body) {
        return this.makeRequest('POST', url, body)
    }

    PUT(url, body) {
        return this.makeRequest('PUT', url, body)
    }

    DELETE(url, body) {
        return this.makeRequest('DELETE', url, body)
    }

}
