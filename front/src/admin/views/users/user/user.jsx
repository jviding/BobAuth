'use strict'
import React from 'react'
import inputStyles from '../../../components/input/textInput.module.scss'
import buttonStyles from '../../../components/button/button.module.scss'
import styles from './user.module.scss'


export default class User extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            newEmail: '',
            newIsAdmin: false,
            isEditing: false,
            isEditingEmail: false,
            isDeleting: false,
            error: ''
        }

        this.deleteUser = this.deleteUser.bind(this)
        this.updateUser = this.updateUser.bind(this)
    }

    deleteUser() {
        window.BobAPI.deleteUser(this.props.user.userID)
        .then(() => {
            this.setState({ isEditing: false })
            this.props.reload()
        })
        .catch((e) => this.setState({ error: e }))
    }

    updateUser() {
        if (!!this.state.newEmail) {
            window.BobAPI.updateUser(this.props.user.userID, this.state.newEmail, this.state.newIsAdmin)
            .then(() => {
                this.setState({ isEditing: false })
                this.props.reload()
            })
            .catch((e) => this.setState({ error: e }))
        } else {
            this.setState({ error: 'Email can\'t be empty!'})
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.userCount !== this.props.userCount) {
            this.setState({ isEditing: false })
        }
    }

    render() {
        if (!this.state.isEditing) {
            return (
                <tbody className={`${styles.tbody} ${styles.closed}`}>
                    <tr>
                        <td>{this.props.user.username}</td>
                        <td>{this.props.user.email}</td>
                        <td>{this.props.user.isAdmin ? "Yes" : "No"}</td>
                        <td>
                            <div
                                className={styles.edit}
                                onClick={() => this.setState({
                                    newEmail: this.props.user.email,
                                    newIsAdmin: this.props.user.isAdmin,
                                    isEditing: true,
                                    isEditingEmail: false,
                                    isDeleting: false,
                                    error: ''
                                })}>
                                Edit
                            </div>
                        </td>
                    </tr>
                </tbody>
            )
        } else {
            return (
                <tbody>
                    <tr>
                        <td colSpan={4}>
                            <table className={styles.inlineTable}>
                                <tbody className={`${styles.tbody} ${styles.open}`}>
                                    <tr>
                                        <td colSpan={4} className={styles.head}>Editing user {this.props.user.username}</td>
                                    </tr>
                                    <tr>
                                        <td>Email:</td>
                                        <td colSpan={2} className={styles.long}>
                                            {this.state.isEditingEmail ?
                                                <input
                                                    className={inputStyles.center}
                                                    type={'text'}
                                                    autoFocus={true}
                                                    placeholder={this.props.user.email}
                                                    value={this.state.newEmail}
                                                    onChange={() => this.setState({ newEmail: event.target.value })} />
                                            : this.state.newEmail}
                                        </td>
                                        <td>
                                            <div
                                                className={styles.edit}
                                                onClick={() => this.setState({ isEditingEmail: !this.state.isEditingEmail })}>
                                                {this.state.isEditingEmail ? "Cancel" : "Change"}
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Admin:</td>
                                        <td colSpan={2} className={styles.long}>{this.state.newIsAdmin ? "Yes" : "No"}</td>
                                        <td>
                                            <div
                                                className={styles.edit}
                                                onClick={() => this.setState({ newIsAdmin: !this.state.newIsAdmin })}>
                                                Change
                                            </div>
                                        </td>
                                    </tr>
                                    <tr className={styles.pullCenter}>
                                        <td colSpan={2}>
                                            <div
                                                className={buttonStyles.btn}
                                                onClick={this.updateUser}>
                                                Save
                                            </div>
                                        </td>
                                        <td colSpan={2}>
                                            <div
                                                className={buttonStyles.btnRed}
                                                onClick={() => this.setState({ isEditing: false })}>
                                                Cancel
                                            </div>
                                        </td>
                                    </tr>
                                    {!this.state.isDeleting &&
                                        <tr>
                                            <td colSpan={4} className={styles.long}>
                                                <div
                                                    className={styles.delete}
                                                    onClick={() => this.setState({ isDeleting: true })}>
                                                    Delete user
                                                </div>
                                            </td>
                                        </tr>
                                    }
                                    {this.state.isDeleting &&
                                        <tr className={styles.pullCenter}>
                                            <td>
                                                <div
                                                    className={styles.submit}
                                                    onClick={this.deleteUser}>
                                                    Yes
                                                </div>
                                            </td>
                                            <td colSpan={2} className={styles.long}>Are you sure?</td>
                                            <td>
                                                <div
                                                    className={styles.edit}
                                                    onClick={() => this.setState({ isDeleting: false })}>
                                                    No
                                                </div>
                                            </td>
                                        </tr>
                                    }
                                    {!!this.state.error &&
                                        <tr>
                                            <td colSpan={4} className={`${styles.long} ${styles.error}`}>
                                                {this.state.error}
                                            </td>
                                        </tr>
                                    }
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            )
        }
    }
}
