'use strict'
import React from 'react'
import buttonStyles from '../../components/button/button.module.scss'

export default class Main extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',

            loaded: '',
            saved: ''
        }
    }

    loadGame() {
        window.BobAPI.loadGame()
        .then((res) => this.setState({ loaded: JSON.stringify(res) }))
        .catch((e) => console.warn(e))
    }

    saveGame() {
        window.BobAPI.saveGame(this.state.username)
        .then((res) => this.setState({ saved: JSON.stringify(res) }))
        .catch((e) => console.warn(e))
    }

    componentDidMount() {
        window.BobAPI.getProfile()
        .then((response) => { this.setState({ username: response.username }) })
        .catch((e) => { console.warn(e) })
    }

    render() {
        if (!!this.props.isAuthenticated) {
            return (
                <div>
                    <h1>Hello, {this.state.username}!</h1>
                    <div
                        className={buttonStyles.btn}
                        onClick={() => this.loadGame()}>
                        Load
                    </div>
                    <br /><br />
                    <div
                        className={buttonStyles.btn}
                        onClick={() => this.saveGame()}>
                        Save
                    </div>
                    <br />
                    <div>Load: {this.state.loaded}</div>
                    <div>Save: {this.state.saved}</div>
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