'use strict'
import React from 'react'
import inputStyles from '../../../components/input/textInput.module.scss'
import buttonStyles from '../../../components/button/button.module.scss'
import styles from './game.module.scss'
import ResourceFile from './components/resourceFile.jsx'
import saveService from './components/saveService.jsx'

export default class Game extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            isEditing: false,
            isDeleting: false,
            newName: '',
            newMainFile: false,
            newResourceFiles: [],
            removedResourceFiles: [],
            error: ''
        }
        this.addNewMainFile = this.addNewMainFile.bind(this)
        this.addResourceFiles = this.addResourceFiles.bind(this)
        this.removeResourceFile = this.removeResourceFile.bind(this)
        this.saveChanges = this.saveChanges.bind(this)
        this.deleteGame = this.deleteGame.bind(this)
    }

    addNewMainFile(file) {
        this.setState({ newMainFile: file })
    }

    addResourceFiles(files) {
        let filesArray = Array.prototype.slice.call(files)
        let newFiles = filesArray.filter((fileA) => this.state.newResourceFiles.every((fileB) => fileA.name !== fileB.name))
        this.setState({ newResourceFiles: this.state.newResourceFiles.concat(newFiles) })
    }

    removeResourceFile(file) {
        this.setState({ removedResourceFiles: this.state.removedResourceFiles.concat([file]) })
    }

    saveChanges() {
        saveService.call(this)
        .then(() => console.log('SUCCESS'))
        .catch((e) => this.setState({ error: e }))
    }

    deleteGame() {
        window.BobAPI.deleteGame(this.props.game.id)
        .then(() => this.props.deleted())
        .catch((e) => this.setState({ error: e }))
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.gameCount !== this.props.gameCount) {
            this.setState({ isEditing: false })
        }
    }

    render() {
        if (!this.state.isEditing) {
            return (
                <tbody className={styles.tbodyClosed}>
                    <tr>
                        <td>{this.props.game.name}</td>
                        <td>
                            <div
                                className={styles.edit}
                                onClick={() => this.setState({
                                    isEditing: true,
                                    isDeleting: false,
                                    newName: this.props.game.name,
                                    newMainFile: false,
                                    newResourceFiles: [],
                                    removedResourceFiles: [],
                                    error: ''
                                })}>
                                Edit
                            </div>
                        </td>
                    </tr>
                </tbody>
            )
        } else {

            const RESOURCE_FILES = this.props.game.resourceFiles.map((file, index) => {
                return (
                    <ResourceFile
                        key={index}
                        name={file}
                        isNew={false}
                        remove={this.removeResourceFile} />
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
                            <h3>Game: {this.props.game.name}</h3>
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
                                                placeholder={this.props.game.name}
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
                                                id={this.props.game.name + "-mainFile"}
                                                onChange={(event) => this.addNewMainFile(event.target.files[0])} />
                                            <label
                                                htmlFor={this.props.game.name + "-mainFile"}
                                                className={styles.submit}>
                                                New
                                            </label>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {!!this.props.game.mainFile &&
                                        <tr>
                                            <td className={styles.red}>{!!this.state.newMainFile ? 'Old:' : '' }</td>
                                            <td className={`${styles.wide} ${styles.textLeft}`}>{this.props.game.mainFile}</td>
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
                                                id={this.props.game.name + "-resourceFiles"}
                                                multiple
                                                onChange={(event) => this.addResourceFiles(event.target.files)} />
                                            <label
                                                htmlFor={this.props.game.name + "-resourceFiles"}
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
                                                    onClick={() => this.setState({ isEditing: false })}>
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
}
