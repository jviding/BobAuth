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
            games: [{
                id: '1',
                name: 'Bob1',
                mainFile: '',
                resourceFiles: []
            }, {
                id: '2',
                name: 'Bob2',
                mainFile: 'bobgame2.html',
                resourceFiles: [ 'res1.js', 'res1.js', 'res1.js' ]
            }, {
                id: '3',
                name: 'Bob3',
                mainFile: 'bobgame3.html',
                resourceFiles: [ 'res1.js', 'res1.js', 'res1.js' ]
            }],
            gameName: '',
            error: ''
        }
        this.createGame = this.createGame.bind(this)
    }

    createGame() {
        if (!!this.state.gameName) {
            window.BobAPI.createGame(this.state.gameName)
            .then(() => console.log('Created!'))
            .catch((e) => this.setState({ error: e }))
        } else {
            this.setState({ error: 'Name cannot be empty!'})
        }
    }

    componentDidMount() {
        window.BobAPI.getGames()
        .then((response) => { this.setState({ games: response.games }) })
        .catch((e) => { console.warn(e) })
    }

    render() {

        const GAMES = this.state.games.map((game, index) => {
            return <Game key={index} game={game} />
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
