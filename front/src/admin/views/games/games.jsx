'use strict'
import React from 'react'
import buttonStyles from '../../components/button/button.module.scss'

export default class Games extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
        }
    }

    componentDidMount() {
        /*window.BobAPI.getProfile()
        .then((response) => { this.setState({ username: response.username }) })
        .catch((e) => { console.warn(e) })*/
    }

    render() {
        return (
            <div>
                <h1>Manage games</h1>
            </div>
        )
    }
}
