'use strict'
import React from 'react'
import styles from './navbar.module.scss'

export default class Navbar extends React.Component {

    constructor(props) {
        super(props)
        this.logout = this.logout.bind(this)
    }

    logout() {
        window.BobAPI.logout()
        .then(() => this.props.wasLoggedOut())
        .catch((e) => console.warn(e))
    }

    render() {
        if (!!this.props.isAuthenticated) {
            return (
                <div className={styles.navbar}>
                    <div
                        className={`${styles.btn} ${styles.bold}`}
                        onClick={() => this.props.navigate('main')}>
                        Bob Games
                    </div>
                    {!!this.props.isAdmin &&
                        <div
                            className={styles.btn}
                            onClick={() => window.open('http://admin.rpylkkanen.com', '_blank')}>
                            Administration
                        </div>
                    }
                    <div className={styles.extender}></div>
                    <div
                        className={styles.btn}
                        onClick={() => this.props.navigate('profile')}>
                        Profile
                    </div>
                    <div
                        className={styles.btn}
                        onClick={this.logout}>
                        Log out
                    </div>
                </div>
            )
        } else {
            return (
                <div className={styles.navbar}>
                    <div
                        className={`${styles.btn} ${styles.bold}`}
                        onClick={() => this.props.navigate('main')}>
                        Bob Games
                    </div>
                    <div className={styles.extender}></div>
                    <div
                        className={styles.btn}
                        onClick={() => this.props.navigate('signup')}>
                        Sign up
                    </div>
                    <div
                        className={styles.btn}
                        onClick={() => this.props.navigate('login')}>
                        Login
                    </div>
                </div>
            )
        }
    }

}
