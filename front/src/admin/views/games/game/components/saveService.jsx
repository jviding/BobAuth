export default function saveService() {

    const updateGameNameIfChanged = () => {
        const HAS_NEW_NAME = this.props.game.name !== this.state.newName
        if (HAS_NEW_NAME) {
            return window.BobAPI.updateGameName(this.props.game.id, this.state.newName)
        } else {
            return Promise.resolve(true)
        }
    }

    const updateMainFileIfChanged = () => {
        const HAS_NEW_MAIN_FILE = !!this.state.newMainFile
        if (HAS_NEW_MAIN_FILE) {
            return window.BobAPI.uploadGameMainFile(this.props.game.id, this.state.newMainFile)
        } else {
            return Promise.resolve(true)
        }
    }

    const deleteResourceFilesIfRemoved = () => {
        const HAS_REMOVED_RESOURCE_FILES = this.state.removedResourceFiles.length > 0
        if (HAS_REMOVED_RESOURCE_FILES) {
            return window.BobAPI.deleteGameResourceFiles(this.props.game.id, this.state.removedResourceFiles)
        } else {
            return Promise.resolve(true)
        }
    }

    const uploadResourceFilesIfAdded = () => {
        const HAS_NEW_RESOURCE_FILES = this.state.newResourceFiles.length > 0
        if (HAS_NEW_RESOURCE_FILES) {
            return Promise.all(
                this.state.newResourceFiles.map((file) =>
                    window.BobAPI.uploadGameResourceFile(this.props.game.id, file)
                )
            )
        } else {
            return Promise.resolve(true)
        }
    }

    return updateGameNameIfChanged()
        .then(updateMainFileIfChanged)
        .then(deleteResourceFilesIfRemoved)
        .then(uploadResourceFilesIfAdded)
}
