export default function saveService() {

    const updateGameIfChanged = () => {
        const HAS_NEW_NAME = this.props.game.name !== this.state.newName
        const HAS_REMOVED_RESOURCE_FILES = this.state.removedResourceFiles.length > 0
        if (HAS_NEW_NAME || HAS_REMOVED_RESOURCE_FILES) {
            return window.BobAPI.updateGame(this.props.game.id, this.state.newName, this.state.removedResourceFiles)
        } else {
            return Promise.resolve(true)
        }
    }

    const uploadFilesIfAdded = () => {
        let filesToUpload = []

        const HAS_NEW_MAIN_FILE = !!this.state.newMainFile
        if (HAS_NEW_MAIN_FILE) {
            filesToUpload.push({ type: 'main', file: this.state.newMainFile })
        }

        const HAS_NEW_RESOURCE_FILES = this.state.newResourceFiles.length > 0
        if (HAS_NEW_RESOURCE_FILES) {
            filesToUpload = filesToUpload.concat(
                this.state.newResourceFiles.map((file) => { return { type: 'resource', file: file }})
            )
        }

        return Promise.all(filesToUpload.map(({type, file}) => {
            return new Promise((resolve, reject) => {
                window.BobAPI.uploadGameFile(this.props.game.id, type, file)
                .then(resolve({ success: true, filename: file.name }))
                .catch(resolve({ success: false, filename: file.name }))
            })
        }))
    }

    return updateGameIfChanged().then(uploadFilesIfAdded)
}
