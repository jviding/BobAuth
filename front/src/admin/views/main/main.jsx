'use strict'
import React from 'react'
import buttonStyles from '../../components/button/button.module.scss'

export default class Main extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            loading: true
        }
    }

    componentDidMount() {
        window.BobAPI.getProfile()
        .then((response) => { this.setState({ username: response.username, loading: false }) })
        .catch((e) => { this.setState({ loading: false }) })
    }

    render() {
        if (this.state.loading) {
            return (
                <div>
                    <h3>Loading...</h3>
                </div>
            )
        } else {
            return (
                <div>
                    {!!this.state.username && <h1>Hello, {this.state.username}!</h1>}
                    {!this.state.username && <h1>Bob Industries LTD</h1>}
                </div>
            )
        }
    }
}
