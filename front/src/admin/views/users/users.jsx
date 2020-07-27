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
    }

    componentDidMount() {
        window.BobAPI.getUsers()
        .then((response) => { this.setState({ users: response.users }) })
        .catch((e) => { console.warn(e) })
    }

    render() {

        const USERS = this.state.users.map((user, key) => {
            return(<User key={key} user={user} />)
        })

        return (
            <div>
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
