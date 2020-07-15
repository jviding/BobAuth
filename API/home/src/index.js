"use strict"

const express = require('express')
const cookieParser = require('cookie-parser')
const app = express()
const port = 3000

app.use(express.json())
app.use(cookieParser())

app.post('/login', (req, res) => {
    (require('./endpoints/login.js'))(req)
    .then((responseCookies) => {
        res.set(responseCookies)
        res.sendStatus(200).end()
    })
    .catch(() => res.sendStatus(404).end())
})

app.post('/logout', (req, res) => {
    (require('./endpoints/logout.js'))(req)
    .then((responseCookies) => {
        res.set(responseCookies)
        res.sendStatus(200).end()
    })
    .catch(() => res.sendStatus(404).end())
})

app.get('/profile', (req, res) => {
    (require('./endpoints/profile/read.js'))(req)
    .then((responseBody) => res.status(200).json(responseBody))
    .catch(() => res.sendStatus(403).end())
})

app.put('/profile', (req, res) => {
    (require('./endpoints/profile/update.js'))(req)
    .then(([responseCookies, responseBody]) => {
        if (!!responseCookies) res.set(responseCookies)
        res.status(200).json(responseBody)
    })
    .catch(() => res.sendStatus(403).end())
})

app.post('/signup', (req, res) => {
    (require('./endpoints/signup.js'))(req)
    .then((responseCookies) => {
        res.set(responseCookies)
        res.sendStatus(200).end()
    })
    .catch(() => res.sendStatus(403).end())
})

app.listen(port, () => console.log('Server running at http://localhost:' + port))
