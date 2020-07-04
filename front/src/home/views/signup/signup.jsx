import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
//import styles from './signup.module.scss'

export default class Signup extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            email: ''
        }
        this.submit = this.submit.bind(this)
    }

    submit() {
        window.BobAPI.signup(this.state.username, this.state.password, this.state.email)
        .then(() => this.props.wasSignedUp())
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
                <input
                    className={inputStyles.basic}
                    type={'text'}
                    placeholder={'Email'}
                    value={this.state.email}
                    onChange={() => this.setState({ email: event.target.value })} />
                <br />
                <div
                    className={buttonStyles.btn}
                    onClick={() => this.submit()}>
                    Sign up
                </div>
            </div>
        )
    }

}
