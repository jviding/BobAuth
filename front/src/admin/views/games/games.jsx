'use strict'
import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
import styles from './games.module.scss'
import Game from './game/game.jsx'

export default class Games extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            games: [],
            gameName: '',
            error: ''
        }
        this.createGame = this.createGame.bind(this)
        this.loadGames = this.loadGames.bind(this)
    }

    createGame() {
        if (!!this.state.gameName) {
            window.BobAPI.createGame(this.state.gameName)
            .then(() => this.loadGames())
            .catch((e) => this.setState({ error: e }))
        } else {
            this.setState({ error: 'Name cannot be empty!'})
        }
    }

    loadGames() {
        window.BobAPI.getGames()
        .then((games) => this.setState({ games: games }))
        .catch((e) => { console.warn(e) })
    }

    componentDidMount() {
        this.loadGames()
    }

    render() {

        const GAMES = this.state.games.map((game, index) => {
            return (
                <Game
                    key={index}
                    game={game}
                    gameCount={this.state.games.length}
                    deleted={this.loadGames} />
                )
        })

        return (
            <div className={styles.tableWrapper}>
                <table>
                    <thead>
                        <tr>
                            <th colSpan={2}>Create game</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <input
                                    className={inputStyles.basic}
                                    type={'text'}
                                    placeholder={'Name'}
                                    value={this.state.gameName}
                                    onChange={() => this.setState({
                                        gameName: event.target.value,
                                        error: ''
                                    })} />
                            </td>
                            <td>
                                <div
                                    className={buttonStyles.btn}
                                    onClick={this.createGame}>
                                    Create
                                </div>
                            </td>
                        </tr>
                        {!!this.state.error &&
                            <tr>
                                <td className={styles.errorMessage}>{this.state.error}</td>
                            </tr>
                        }
                    </tbody>
                    <thead>
                        <tr>
                            <th className={styles.title}>Games</th>
                            <th></th>
                        </tr>
                    </thead>
                    {GAMES}
                </table>
            </div>
        )
    }
}
