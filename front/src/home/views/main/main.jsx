'use strict'
import React from 'react'
import buttonStyles from '../../components/button/button.module.scss'
import Game from './game.jsx'

export default class Main extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            games: []
        }
        this.loadGames = this.loadGames.bind(this)
    }

    loadGames() {
        window.BobAPI.getGames()
        .then((res) => this.setState({ games: res }))
        .catch((e) => console.warn(e))
    }

    componentDidMount() {
        this.loadGames()
    }

    render() {
        if (!!this.props.isAuthenticated) {

            const GAMES = this.state.games.map((game, i) => {
                return (<Game key={i} id={game.id} name={game.name} />)
            })

            return (
                <div>
                    <h1>Play a game?</h1>
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th></th>
                            </tr>
                        </thead>
                        {GAMES}
                    </table>
                </div>
            )
        } else {
            return (
                <div>
                    <h1>Bob Industries LTD</h1>
                </div>
            )
        }
    }
}
