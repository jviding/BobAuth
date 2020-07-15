import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
import styles from './login.module.scss'

export default class Login extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            errorMessage: ''
        }
        this.onChangeHandler = this.onChangeHandler.bind(this)
        this.submit = this.submit.bind(this)
    }

    onChangeHandler(key, value) {
        this.setState({ [key]: value, errorMessage: '' })
    }

    submit() {
        // TODO: Show server errors with errorMessage
        const HAS_USERNAME = !!this.state.username
        const HAS_PASSWORD = !!this.state.password
        if (HAS_USERNAME && HAS_PASSWORD) {
            window.BobAPI.login(this.state.username, this.state.password)
            .then(() => this.props.wasLoggedIn())
            .catch((e) => console.warn(e))
        } else if (!HAS_USERNAME) {
            this.setState({ errorMessage: 'Username missing!' })
        } else if (!HAS_PASSWORD) {
            this.setState({ errorMessage: 'Password missing!' })
        }
    }

    render() {
        return (
            <div>
                <table>
                    <tbody>
                        <tr>
                            <td className={styles.cell}>Username:</td>
                            <td className={styles.cell}>
                                <input
                                    className={inputStyles.basic}
                                    type={'text'}
                                    placeholder={'Bob'}
                                    autoFocus={true}
                                    value={this.state.username}
                                    onChange={() => this.onChangeHandler('username', event.target.value)} />
                            </td>
                        </tr>
                        <tr>
                            <td className={styles.cell}>Password:</td>
                            <td className={styles.cell}>
                                <input
                                    className={inputStyles.basic}
                                    type={'password'}
                                    placeholder={'*****'}
                                    value={this.state.password}
                                    onChange={() => this.onChangeHandler('password', event.target.value)} />
                            </td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <div
                                    className={buttonStyles.btn}
                                    onClick={() => this.submit()}>
                                    Login
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                {!!this.state.errorMessage &&
                    <div className={styles.errorMessage}>{this.state.errorMessage}</div>
                }
            </div>
        )
    }

}
