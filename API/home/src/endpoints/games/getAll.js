"use strict";

const API = require('../../lib/internalAPI/API.js')

module.exports = (req, res) => {

    API.getGames()
    .then((response) => res.status(200).json(JSON.parse(response.body)))
    .catch(() => res.sendStatus(500).end())
}
