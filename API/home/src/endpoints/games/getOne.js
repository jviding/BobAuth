"use strict";

const API = require('../../lib/internalAPI/API.js')

module.exports = (req, res) => {

    const GAME_ID = req.params.gameID

    API.getGame(GAME_ID)
    .then((response) => {
        const GAME = JSON.parse(response.body)
        const FILE = !!GAME.mainFile.length > 0 ? GAME.mainFile[0] : false
        const PATH = !!FILE ? `${GAME.id}.game.rpylkkanen.com/${FILE}` : ''
        res.status(200).json({
            id: GAME.id,
            name: GAME.name,
            path: PATH
        })
    })
    .catch(() => res.sendStatus(500).end())
}
