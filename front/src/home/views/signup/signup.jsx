import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
import styles from './signup.module.scss'

export default class Signup extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            email: '',
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
        const HAS_EMAIL = !!this.state.email
        if (HAS_USERNAME && HAS_PASSWORD && HAS_EMAIL) {
            window.BobAPI.signup(this.state.username, this.state.password, this.state.email)
            .then(() => this.props.wasSignedUp())
            .catch((e) => console.warn(e))
        } else if (!HAS_USERNAME) {
            this.setState({ errorMessage: 'Username can not be empty!'})
        } else if (!HAS_PASSWORD) {
            this.setState({ errorMessage: 'Password can not be empty!'})
        } else if (!HAS_EMAIL) {
            this.setState({ errorMessage: 'Email can not be empty!'})
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
                            <td className={styles.cell}>Email:</td>
                            <td className={styles.cell}>
                                <input
                                    className={inputStyles.basic}
                                    type={'text'}
                                    placeholder={'bob@example.com'}
                                    value={this.state.email}
                                    onChange={() => this.onChangeHandler('email', event.target.value)} />
                            </td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <div
                                    className={buttonStyles.btn}
                                    onClick={() => this.submit()}>
                                    Sign up
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
