import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
//import styles from './signup.module.scss'

export default class Login extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: ''
        }
        this.submit = this.submit.bind(this)
    }

    submit() {
        window.BobAPI.login(this.state.username, this.state.password)
        .then(() => this.props.wasLoggedIn())
        .catch((e) => console.warn(e))
    }

    render() {
        return (
            <div>
                <input
                    className={inputStyles.basic}
                    type={'text'}
                    placeholder={'Username'}
                    autoFocus={true}
                    value={this.state.username}
                    onChange={() => this.setState({ username: event.target.value })} />
                <input
                    className={inputStyles.basic}
                    type={'password'}
                    placeholder={'Password'}
                    value={this.state.password}
                    onChange={() => this.setState({ password: event.target.value })} />
                <br />
                <div
                    className={buttonStyles.btn}
                    onClick={() => this.submit()}>
                    Login
                </div>
            </div>
        )
    }

}
