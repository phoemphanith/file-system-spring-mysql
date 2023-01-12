package com.asydash.filestoragerestfulapi.exception

class FileNotFoundException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(message: String?, cause: Throwable?): super(message, cause)
}