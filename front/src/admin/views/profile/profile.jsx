'use strict'
import React from 'react'
import inputStyles from '../../components/input/textInput.module.scss'
import buttonStyles from '../../components/button/button.module.scss'
import styles from './profile.module.scss'

export default class Profile extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            editing: false,
            username: '',
            email: '',
            newEmail: '',
            password: '',
            newPasswordFirst: '',
            newPasswordSecond: '',
            errorMessage: ''
        }
        this.onChangeHandler = this.onChangeHandler.bind(this)
        this.submit = this.submit.bind(this)
    }

    onChangeHandler(key, value) {
        this.setState({
            [key]: value,
            editing: true,
            errorMessage: ''
        })
    }

    submit() {
        // TODO: Show server errors with errorMessage
        const HAS_EMAIL = !!this.state.newEmail
        const VALID_NEW_PASSWORD = !this.state.newPasswordFirst || this.state.newPasswordFirst === this.state.newPasswordSecond
        const HAS_PASSWORD = !!this.state.password

        if (HAS_PASSWORD && HAS_EMAIL && VALID_NEW_PASSWORD) {
            window.BobAPI.updateProfile(this.state.newEmail, this.state.newPasswordFirst, this.state.password)
            .then((response) => this.setState({
                editing: false,
                email: response.email,
                newEmail: response.email,
                password: '',
                newPasswordFirst: '',
                newPasswordSecond: '',
                errorMessage: ''
            }))
            .catch((e) => console.warn(e))
        } else if (!HAS_EMAIL) {
            this.setState({ errorMessage: 'Email can not be empty!'})
        } else if (!VALID_NEW_PASSWORD) {
            this.setState({ errorMessage: 'New and repeated password don\'t match!'})
        } else if (!HAS_PASSWORD) {
            this.setState({ errorMessage: 'Current password is missing!'})
        }
    }

    componentDidMount() {
        /*window.BobAPI.getProfile()
        .then((response) => {
            this.setState({
                username: response.username,
                email: response.email,
                newEmail: response.email
            })
        })
        .catch((e) => console.warn(e))*/
    }

    render() {
        return (
            <div>
                <table>
                    <tbody>
                        <tr>
                            <td className={styles.cell}>Username:</td>
                            <td className={styles.cell}>{this.state.username}</td>
                        </tr>
                        <tr>
                            <td className={styles.cell}>Email:</td>
                            <td className={styles.cell}>
                                <input
                                    className={inputStyles.basic}
                                    type={'text'}
                                    placeholder={this.state.email}
                                    value={this.state.newEmail}
                                    onChange={() => this.onChangeHandler('newEmail', event.target.value)} />
                            </td>
                        </tr>
                        <tr>
                            <td className={styles.cell}>New password:</td>
                            <td className={styles.cell}>
                                <input
                                    className={inputStyles.basic}
                                    type={'password'}
                                    placeholder={'*****'}
                                    value={this.state.newPasswordFirst}
                                    onChange={() => this.onChangeHandler('newPasswordFirst', event.target.value)} />
                            </td>
                        </tr>
                        {!!this.state.newPasswordFirst &&
                            <tr>
                                <td className={styles.cell}>Repeat password:</td>
                                <td className={styles.cell}>
                                    <input
                                        className={inputStyles.basic}
                                        type={'password'}
                                        placeholder={'*****'}
                                        value={this.state.newPasswordSecond}
                                        onChange={() => this.onChangeHandler('newPasswordSecond', event.target.value)} />
                                </td>
                            </tr>
                        }
                        {!!this.state.editing &&
                            [<tr key={1}>
                                <td colSpan={2}>
                                    <div className={styles.blackLine}></div>
                                </td>
                            </tr>,
                            <tr key={2}>
                                <td className={styles.cell}>Current password:</td>
                                <td className={styles.cell}>
                                    <input
                                        className={inputStyles.basic}
                                        type={'password'}
                                        placeholder={'*****'}
                                        value={this.state.password}
                                        onChange={() => this.onChangeHandler('password', event.target.value)} />
                                </td>
                            </tr>,
                            <tr key={3}>
                                <td className={styles.cell}></td>
                                <td className={`${styles.cell} ${styles.right}`}>
                                    <div
                                        className={buttonStyles.btn}
                                        onClick={() => this.submit()}>
                                        Submit
                                    </div>
                                </td>
                            </tr>]
                        }
                    </tbody>
                </table>
                {!!this.state.errorMessage &&
                    <div className={styles.errorMessage}>{this.state.errorMessage}</div>
                }
            </div>
        )
    }

}
