'use strict'

export default class Requests {

    constructor() {
        this.makeRequest = this.makeRequest.bind(this)
        this.GET = this.GET.bind(this)
        this.POST = this.POST.bind(this)
        this.POSTFile = this.POSTFile.bind(this)
        this.PUT = this.PUT.bind(this)
        this.DELETE = this.DELETE.bind(this)
    }

    makeRequest(method, url, body = null, isJson = true) {
        return new Promise((resolve, reject) => {
            var req = new XMLHttpRequest()
            req.onerror = () => reject("Network failure")
            req.onload = () => (req.status === 200 ? resolve(JSON.parse(req.responseText)) : reject(req.responseText))
            req.open(method, url)
            if (body !== null && isJson) {
                body = JSON.stringify(body)
                req.setRequestHeader('Content-Type', 'application/json')
            }
            req.send(body)
        })
    }

    GET(url, params = false) {
        if (!!params) {
            // TODO: UrlParams as Base64
            url += '?' + Object.entries(params).map(([k, v]) => { return k + '=' + v }).join('&')
        }
        return this.makeRequest('GET', url)
    }

    POST(url, body) {
        return this.makeRequest('POST', url, body)
    }

    POSTFile(url, formData) {
        return this.makeRequest('POST', url, formData, false)
    }

    PUT(url, body) {
        return this.makeRequest('PUT', url, body)
    }

    DELETE(url, body) {
        return this.makeRequest('DELETE', url, body)
    }

}
