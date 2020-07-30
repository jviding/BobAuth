'use strict'
import React from 'react'
import styles from './users.module.scss'
import User from './user/user.jsx'


export default class Users extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            users: []
        }
        this.loadUsers = this.loadUsers.bind(this)
    }

    sortAlphabetical() {
        users.sort((a, b) => a.firstname.localeCompare(b.firstname))
    }

    loadUsers() {
        window.BobAPI.getUsers()
        .then((response) => {
            const SORTED_USERS = response.users.sort((a, b) => a.username.localeCompare(b.username))
            this.setState({users: SORTED_USERS })
        })
        .catch(console.error)
    }

    componentDidMount() {
        this.loadUsers()
    }

    render() {

        const USERS = this.state.users.map((user, key) => {
            return(<User key={key} user={user} reload={this.loadUsers} />)
        })

        return (
            <div className={styles.tableWrapper}>
                <table>
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Admin</th>
                            <th></th>
                        </tr>
                    </thead>
                    {USERS}
                </table>
            </div>
        )
    }
}
