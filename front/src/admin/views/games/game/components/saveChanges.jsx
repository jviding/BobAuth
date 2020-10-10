export default function saveChanges() {

    const updateName = () => {
        if (!!this.state.newName && this.state.newName !== this.state.name) {
            return window.BobAPI.updateGame(this.props.id, this.state.newName)
        } else {
            return Promise.resolve(true)
        }
    }

    const deleteResourceFiles = () => {
        return Promise.all(
            this.state.removedResourceFiles.map((filename) => {
                return window.BobAPI.deleteGameFile(this.props.id, 'resource', filename)
            })
        )
    }

    const uploadFiles = () => {
        return Promise.all(
            (!!this.state.newMainFile ? [{type: 'main', file: this.state.newMainFile}] : [])
            .concat(this.state.newResourceFiles.map((f) => { return {type: 'resource', file: f}}))
            .map((entry) => { return window.BobAPI.uploadGameFile(this.props.id, entry.type, entry.file)})
        )
    }

    return updateName().then(deleteResourceFiles).then(uploadFiles)
}
