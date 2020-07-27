'use strict'
import React from 'react'
import inputStyles from '../../../components/input/textInput.module.scss'
import buttonStyles from '../../../components/button/button.module.scss'
import styles from './user.module.scss'


export default class User extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            email: '',
            isAdmin: false
        }
    }

    componentDidMount() {
        this.setState({
            username: this.props.user.username,
            email: this.props.user.email,
            isAdmin: this.props.user.isAdmin
        })
    }

    render() {

        return (
            <tbody>
                <tr>
                    <td>{this.state.username}</td>
                    <td>{this.state.email}</td>
                    <td>{this.state.isAdmin ? "Yes" : "No"}</td>
                    <td>Edit</td>
                </tr>
            </tbody>
        )
    }
}
