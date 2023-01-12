package com.asydash.filestoragerestfulapi.payload.dto

data class FileDTO(
    val id: Long?,
    var fileName: String,
    var fileUri: String,
    var fileType: String,
    var size: Long?,
)
