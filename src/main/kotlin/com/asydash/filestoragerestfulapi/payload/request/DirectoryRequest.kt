package com.asydash.filestoragerestfulapi.payload.request

data class DirectoryRequest(
    var name: String,
    var parentId: Long?
)
