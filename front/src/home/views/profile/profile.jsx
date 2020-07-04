import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
//import styles from './signup.module.scss'

export default class Profile extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            email: '',
            newPassword: ''
        }
    }

    componentDidMount() {
        window.BobAPI.profile()
        .then((response) => {
            this.setState({ username: response.username, email: response.email })
        })
        .catch((e) => console.warn(e))
    }

    render() {
        return (
            <div>
                <p>Username: {this.state.username}</p>
                <p>Email: {this.state.email}</p>
                <p>Change password</p>
            </div>
        )
    }

}
