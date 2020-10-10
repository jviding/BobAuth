'use strict'
import React from 'react'
import styles from '../game.module.scss'
import inputStyles from '../../../../components/input/textInput.module.scss'
import buttonStyles from '../../../../components/button/button.module.scss'
import ResourceFile from './resourceFile.jsx'
import save from './saveChanges.jsx'

export default class GameBody extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            name: '',
            newName: '',
            mainFile: '',
            newMainFile: null,
            resourceFiles: [],
            newResourceFiles: [],
            removedResourceFiles: [],
            isDeleting: false,
            error: ''
        }
        this.addResourceFiles = this.addResourceFiles.bind(this)
        this.saveChanges = this.saveChanges.bind(this)
        this.deleteGame = this.deleteGame.bind(this)
        this.loadGame = this.loadGame.bind(this)
    }

    addResourceFiles(files) {
        let filesArray = Array.prototype.slice.call(files)
        let newFiles = filesArray.filter((fileA) => this.state.newResourceFiles.every((fileB) => fileA.name !== fileB.name))
        this.setState({ newResourceFiles: this.state.newResourceFiles.concat(newFiles) })
    }

    saveChanges() {
        // TODO: Show "loading..." to prevent multiple calls
        // TODO: Show success/failure per request
        save.call(this)
        .then(() => {
            this.loadGame()
            this.props.update()
        })
        .catch((e) => this.setState({ error: e }))
    }

    deleteGame() {
        window.BobAPI.deleteGame(this.props.id)
        .then(() => this.props.update())
        .catch((e) => this.setState({ error: e }))
    }

    loadGame() {
        window.BobAPI.getGame(this.props.id)
        .then((res) => this.setState({
            name: res.name,
            newName: '',
            mainFile: res.mainFile.length > 0 ? res.mainFile[0] : '',
            newMainFile: null,
            resourceFiles: res.resourceFiles,
            newResourceFiles: [],
            removedResourceFiles: [],
            isDeleting: false,
            error: ''
        }))
        .catch(console.warn)
    }

    componentDidMount() {
        this.loadGame()
    }

    render() {

        const RESOURCE_FILES = this.state.resourceFiles.map((name, index) => {
            return (
                <ResourceFile
                    key={index}
                    name={name}
                    isNew={false}
                    remove={(name) => this.setState({
                        removedResourceFiles: this.state.removedResourceFiles.concat([name])
                    })} />
            )
        })

        const RESOURCE_FILES_NEW = this.state.newResourceFiles.map((file, index) => {
            return (
                <ResourceFile
                    key={index}
                    name={file.name}
                    isNew={true} />
            )
        })

        return (
            <tbody className={styles.tbodyOpen}>
                <tr>
                    <td colSpan={2}>
                        <h3>Game: {this.state.name}</h3>
                        <table>
                            <thead>
                                <tr>
                                    <th colSpan={2}>Name</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td colSpan={2}>
                                        <input
                                            className={inputStyles.basic}
                                            type={'text'}
                                            placeholder={this.state.name}
                                            value={this.state.newName}
                                            onChange={() => this.setState({ newName: event.target.value })} />
                                    </td>
                                    <td></td>
                                </tr>
                            </tbody>
                            <thead>
                                <tr>
                                    <th colSpan={2} className={styles.wide}>Main file</th>
                                    <th>
                                        <input
                                            hidden={true}
                                            type={"file"}
                                            id={this.props.id + "-mainFile"}
                                            onChange={(event) => this.setState({ newMainFile: event.target.files[0] })} />
                                        <label
                                            htmlFor={this.props.id + "-mainFile"}
                                            className={styles.submit}>
                                            New
                                        </label>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {!!this.state.mainFile &&
                                    <tr>
                                        <td className={styles.red}>{!!this.state.newMainFile ? 'Old:' : '' }</td>
                                        <td className={`${styles.wide} ${styles.textLeft}`}>{this.state.mainFile}</td>
                                        <td></td>
                                    </tr>
                                }
                                {!!this.state.newMainFile &&
                                    <tr>
                                        <td className={styles.green}>New:</td>
                                        <td className={`${styles.wide} ${styles.textLeft}`}>{this.state.newMainFile.name}</td>
                                        <td></td>
                                    </tr>
                                }
                            </tbody>
                            <thead>
                                <tr>
                                    <th colSpan={2}>Resource files</th>
                                    <th>
                                        <input
                                            hidden={true}
                                            type={"file"}
                                            id={this.props.id + "-resourceFiles"}
                                            multiple
                                            onChange={(event) => this.addResourceFiles(event.target.files)} />
                                        <label
                                            htmlFor={this.props.id + "-resourceFiles"}
                                            className={styles.submit}>
                                            Add
                                        </label>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {RESOURCE_FILES}
                                {RESOURCE_FILES_NEW}
                            </tbody>
                            <tbody>
                                <tr>
                                    <td colSpan={3}>
                                        <div>
                                            <div
                                                className={`${buttonStyles.btn} ${styles.btn}`}
                                                onClick={this.saveChanges}>
                                                Save
                                            </div>
                                            <div
                                                className={`${buttonStyles.btnRed} ${styles.btn}`}
                                                onClick={this.props.close}>
                                                Cancel
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                            <tbody>
                                <tr>
                                    <td colSpan={3}>
                                        {!this.state.isDeleting &&
                                            <div
                                                className={styles.delete}
                                                onClick={() => this.setState({ isDeleting: true })}>
                                                Delete game
                                            </div>
                                        }
                                        {this.state.isDeleting &&
                                            <div className={styles.confirm}>
                                                <div
                                                    className={styles.submit}
                                                    onClick={this.deleteGame}>
                                                    Yes
                                                </div>
                                                <div className={styles.fill}>Are you sure?</div>
                                                <div
                                                    className={styles.edit}
                                                    onClick={() => this.setState({ isDeleting: false })}>
                                                    No
                                                </div>
                                            </div>
                                        }
                                    </td>
                                </tr>
                            </tbody>
                            {!!this.state.error &&
                                <tbody>
                                    <tr>
                                        <td colSpan={3} className={`${styles.red} ${styles.eMsg}`}>{this.state.error}</td>
                                    </tr>
                                </tbody>
                            }
                        </table>
                    </td>
                </tr>
            </tbody>
        )
    }
}
