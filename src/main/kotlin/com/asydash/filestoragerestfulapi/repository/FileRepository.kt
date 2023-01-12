package com.asydash.filestoragerestfulapi.repository

import com.asydash.filestoragerestfulapi.model.File
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository: JpaRepository<File, Long> {
    fun findAllByDirectoryId(directoryId: Long?): List<File>
}