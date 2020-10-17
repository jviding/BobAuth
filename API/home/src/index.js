"use strict";

const express = require('express')
const cookieParser = require('cookie-parser')
const app = express()
const port = 3000

app.use(express.json())
app.use(cookieParser())

app.use((require('./lib/logHttpReq.js')))

app.post('/signup', (require('./endpoints/signup.js')))
app.post('/login', (require('./endpoints/login.js')))
app.post('/logout', (require('./endpoints/logout.js')))

// REQUIRE AUTHENTICATION FOR ENDPOINTS NEXT
app.use((require('./lib/isAuthenticated.js')))

app.get('/profile', (require('./endpoints/profile/read.js')))
app.put('/profile', (require('./endpoints/profile/update.js')))
app.get('/games', (require('./endpoints/games/getAll.js')))
//app.get('/game/:gameID/load', (require('./endpoints/games/load.js')))
//app.put('/game/:gameID/save', (require('./endpoints/games/save.js')))
app.get('/game/:gameID', (require('./endpoints/games/getOne.js')))

// CATCH ALL
app.use((req, res) => res.sendStatus(403).end())

app.listen(port, () => console.log('Server running at http://localhost:' + port))
