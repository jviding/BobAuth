import React from 'react'

export default class Main extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: ''
        }
    }

    componentDidMount() {
        window.BobAPI.getProfile()
        .then((response) => { this.setState({ username: response.username }) })
        .catch((e) => { console.warn(e) })
    }

    render() {
        if (!!this.props.isAuthenticated) {
            return (
                <div>
                    <h1>Hello, {this.state.username}!</h1>
                </div>
            )
        } else {
            return (
                <div>
                    <h1>Bob Industries LTD</h1>
                </div>
            )
        }
    }
}
