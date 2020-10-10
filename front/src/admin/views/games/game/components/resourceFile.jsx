'use strict'
import React from 'react'
import styles from '../game.module.scss'

export default class ResourceFile extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            isHovered: false,
            state: ''
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.name !== this.props.name) {
            this.setState({ state: '' })
        }
    }

    componentDidMount() {
        if (this.props.isNew) {
            this.setState({ state: 'new' })
        }
    }

    render() {

        const FILENAME_STYLE = this.state.isHovered ? `${styles.wide} ${styles.textLeft} ${styles.red}` : `${styles.wide} ${styles.textLeft}`
        const FILENAME = <td className={FILENAME_STYLE}>{this.props.name}</td>

        switch(this.state.state) {
            case 'new':
                return (
                    <tr>
                        <td className={styles.green}>New:</td>
                        {FILENAME}
                        <td></td>
                    </tr>
                )
            case 'removed':
                return (
                    <tr>
                        <td className={styles.red}>Remove:</td>
                        {FILENAME}
                        <td></td>
                    </tr>
                )
            default:
                return (
                    <tr>
                        <td></td>
                        {FILENAME}
                        <td>
                            <div
                                className={styles.edit}
                                onMouseEnter={() => this.setState({ isHovered: true })}
                                onMouseLeave={() => this.setState({ isHovered: false })}
                                onClick={() => {
                                    this.props.remove(this.props.name)
                                    this.setState({ state: 'removed', isHovered: false })
                                }}>
                                X
                            </div>
                        </td>
                    </tr>
                )
        }
    }
}
