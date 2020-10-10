'use strict'
import React from 'react'
import styles from './game.module.scss'
import GameBody from './components/gameBody.jsx'

export default class Game extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            isEditing: false,
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.gameCount !== this.props.gameCount || prevProps.id !== this.props.id) {
            this.setState({ isEditing: false })
        }
    }

    render() {
        if (!this.state.isEditing) {
            return (
                <tbody className={styles.tbodyClosed}>
                    <tr>
                        <td>{this.props.name}</td>
                        <td>
                            <div
                                className={styles.edit}
                                onClick={() => this.setState({ isEditing: true })}>
                                Edit
                            </div>
                        </td>
                    </tr>
                </tbody>
            )
        } else {
            return (
                <GameBody
                    id={this.props.id}
                    update={this.props.update}
                    close={() => this.setState({ isEditing: false })} />
            )
        }
    }
}
