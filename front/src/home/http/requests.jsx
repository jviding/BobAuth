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
                resolve({
                    status: req.status,
                    response: req.responseText
                })
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

    GET(url, params) {
        let urlParams = ''
        if (params.length > 0) {
            urlParams = params.reduce((query, param) => {
                return query + '&' + param.key + '=' + param.value
            }, '')
            urlParams = '?' + urlParams.substr(1)
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
