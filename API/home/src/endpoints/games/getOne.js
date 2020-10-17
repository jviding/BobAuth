"use strict";

const API = require('../../lib/internalAPI/API.js')

module.exports = (req, res) => {

    const GAME_ID = req.params.gameID

    API.getGame(GAME_ID)
    .then((response) => res.status(200).json(JSON.parse(response.body)))
    .catch(() => res.sendStatus(500).end())
}
