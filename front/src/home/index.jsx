'use strict'
import React from 'react'
import ReactDOM from 'react-dom'
import Navbar from './views/navbar/navbar.jsx'
import Main from './views/main/main.jsx'
import Signup from './views/signup/signup.jsx'
import Login from './views/login/login.jsx'
import Profile from './views/profile/profile.jsx'
import styles from './styles.module.scss'

import BobAPI from './http/apiCalls.jsx'
window.BobAPI = new BobAPI()

class Index extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            view: 'main',
            isAuthenticated: false,
            isAdmin: false
        }
        this.checkSession = this.checkSession.bind(this)
    }

    checkSession() {
        window.BobAPI.profile()
        .then((response) => {
            this.setState({ view: 'main', isAuthenticated: true, isAdmin: !!response.isAdmin })
        })
        .catch((e) => {
            console.warn(e)
            this.setState({ view: 'main', isAuthenticated: false, isAdmin: false })
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
                    isAdmin={this.state.isAdmin}
                    wasLoggedOut={this.checkSession} />
                <div className={styles.a}>
                    <div className={styles.b}>
                        <div className={styles.c}>
                            <div className={styles.jumbotron}>
                                {this.state.view === 'main' && <Main isAuthenticated={this.state.isAuthenticated} />}
                                {this.state.view === 'signup' && <Signup wasSignedUp={this.checkSession} />}
                                {this.state.view === 'login' && <Login wasLoggedIn={this.checkSession} />}
                                {this.state.view === 'profile' && <Profile />}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Index />, document.getElementById('root'))
