package com.asydash.filestoragerestfulapi.service

import com.asydash.filestoragerestfulapi.model.Directory
import com.asydash.filestoragerestfulapi.payload.dto.DirectoryDTO
import com.asydash.filestoragerestfulapi.payload.request.DirectoryRequest
import com.asydash.filestoragerestfulapi.repository.DirectoryRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class DirectoryService(private val repository: DirectoryRepository)
{
    fun getAll(parentId: Long?): List<DirectoryDTO> = repository.findAllByParentId(parentId)
        .map { item: Directory -> DirectoryDTO(item.id, item.name) }

    fun find(id: Long): Directory? = repository.findById(id).orElse(null)

    fun create(data: DirectoryRequest): Directory{
        val newDirectory = Directory(name = data.name, parentId = data.parentId)
        try {
            return repository.save(newDirectory)
        }catch (ex: RuntimeException){
            throw RuntimeException("Error: $ex")
        }
    }

    fun updateName(id: Long, data: DirectoryRequest): Directory?{
        val directory: Directory? = repository.findById(id).map {
            current: Directory ->
                current.copy(
                    name = data.name,
                    parentId = data.parentId
                )
        }.orElse(null)

        if(directory != null) return repository.save(directory)

        throw RuntimeException("Not found")
    }

    fun delete(id: Long): Boolean{
        try {
            repository.deleteById(id)
            repository.deleteAllByParentId(id)
            return true
        }catch (ex: RuntimeException){
            throw RuntimeException("Error: $ex")
        }
    }
}