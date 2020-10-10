'use strict'
import React from 'react'
import ReactDOM from 'react-dom'
import Navbar from './views/navbar/navbar.jsx'
import Main from './views/main/main.jsx'
import Games from './views/games/games.jsx'
import Login from './views/login/login.jsx'
import Profile from './views/profile/profile.jsx'
import Users from './views/users/users.jsx'
import styles from './styles.module.scss'

import BobAPI from './http/apiCalls.jsx'
window.BobAPI = new BobAPI()

class Index extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            view: '',
            isAuthenticated: false
        }
        this.checkSession = this.checkSession.bind(this)
    }

    checkSession() {
        //TODO: index.js uncomment checkSession()
        //TODO: If user is removed, leaves next block open?
        window.BobAPI.getProfile()
        .then((response) => {
            this.setState({ view: 'main', isAuthenticated: true })
        })
        .catch((e) => {
            console.warn(e)
            this.setState({ view: 'main', isAuthenticated: false })
        })
    }

    componentDidMount() {
        this.checkSession()
    }

    render() {
        return (
            <div>
                <Navbar
                    navigate={(newView) => this.setState({ view: newView })}
                    isAuthenticated={this.state.isAuthenticated}
                    wasLoggedOut={this.checkSession} />
                <div className={styles.a}>
                    <div className={styles.b}>
                        <div className={styles.c}>
                            <div className={styles.jumbotron}>
                                {this.state.view === 'main' && <Main />}
                                {this.state.view === 'games' && <Games />}
                                {this.state.view === 'login' && <Login wasLoggedIn={this.checkSession} />}
                                {this.state.view === 'profile' && <Profile />}
                                {this.state.view === 'users' && <Users />}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Index />, document.getElementById('root'))
