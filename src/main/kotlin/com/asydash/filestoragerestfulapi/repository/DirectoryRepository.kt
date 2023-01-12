package com.asydash.filestoragerestfulapi.repository

import com.asydash.filestoragerestfulapi.model.Directory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DirectoryRepository: JpaRepository<Directory, Long> {
    fun findAllByParentId(parentId: Long?): List<Directory>
    fun deleteAllByParentId(parentId: Long?): Boolean
}