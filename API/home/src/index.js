"use strict";

const express = require('express')
const cookieParser = require('cookie-parser')
const app = express()
const port = 3000

app.use(express.json())
app.use(cookieParser())

app.get('/games/load', (req, res) => {
    (require('./endpoints/games/load.js'))(req)
    .then((body) => res.status(200).json(body))
    .catch((e) => {
        console.log(e)
        return Promise.reject()
    })
    .catch(() => res.sendStatus(403).end())
})

app.put('/games/:gameName/save', (req, res) => {
    res.sendStatus(404).end()
})

app.post('/login', (req, res) => {
    (require('./endpoints/login.js'))(req)
    .then((cookies) => {
        res.set({ 'set-cookie': cookies })
        res.sendStatus(200).end()
    })
    .catch(() => res.sendStatus(404).end())
})

app.post('/logout', (req, res) => {
    (require('./endpoints/logout.js'))(req)
    .then((cookies) => {
        res.set({ 'set-cookie': cookies })
        res.sendStatus(200).end()
    })
    .catch(() => res.sendStatus(404).end())
})

app.get('/profile', (req, res) => {
    (require('./endpoints/profile/read.js'))(req)
    .then((body) => res.status(200).json(body))
    .catch(() => res.sendStatus(403).end())
})

app.put('/profile', (req, res) => {
    (require('./endpoints/profile/update.js'))(req)
    .then(([cookies, body]) => {
        if (!!cookies) res.set({ 'set-cookie': cookies })
        res.status(200).json(body)
    })
    .catch(() => res.sendStatus(403).end())
})

app.post('/signup', (req, res) => {
    (require('./endpoints/signup.js'))(req)
    .then((cookies) => {
        res.set({ 'set-cookie': cookies })
        res.sendStatus(200).end()
    })
    .catch(() => res.sendStatus(403).end())
})

app.listen(port, () => console.log('Server running at http://localhost:' + port))
