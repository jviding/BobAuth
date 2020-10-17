"use strict";

const express = require('express')
const cookieParser = require('cookie-parser')
const app = express()
const port = 3000

app.use(express.json())
app.use(cookieParser())

app.use((require('./lib/logHttpReq.js')))

app.get('/games/load', (require('./endpoints/games/load.js')))
app.put('/games/save', (require('./endpoints/games/save.js')))

app.post('/login', (require('./endpoints/login.js')))
app.post('/logout', (require('./endpoints/logout.js')))

app.get('/profile', (require('./endpoints/profile/read.js')))
app.put('/profile', (require('./endpoints/profile/update.js')))

app.post('/signup', (require('./endpoints/signup.js')))

app.use((req, res) => res.sendStatus(403).end())

app.listen(port, () => console.log('Server running at http://localhost:' + port))
