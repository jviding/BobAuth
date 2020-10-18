'use strict'
import React from 'react'
import buttonStyles from '../../components/button/button.module.scss'

export default class Main extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
        }
    }

    render() {
        return (
            <tbody>
                <tr>
                    <td>{this.props.name}</td>
                    <td>Play</td>
                </tr>
            </tbody>
        )
    }
}
