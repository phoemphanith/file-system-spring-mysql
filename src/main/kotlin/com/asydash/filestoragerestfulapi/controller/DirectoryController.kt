package com.asydash.filestoragerestfulapi.controller

import com.asydash.filestoragerestfulapi.model.Directory
import com.asydash.filestoragerestfulapi.payload.dto.DirectoryDTO
import com.asydash.filestoragerestfulapi.payload.request.DirectoryRequest
import com.asydash.filestoragerestfulapi.payload.response.ResponseObjectMap
import com.asydash.filestoragerestfulapi.service.DirectoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("/api/file-storage")
class DirectoryController
{
    @Autowired
    lateinit var response: ResponseObjectMap
    @Autowired
    lateinit var dirService: DirectoryService

    @PostMapping("/directory")
    fun createDirectory(@RequestBody body: DirectoryRequest): MutableMap<String, Any> {
        dirService.create(body)
        return response.responseCodeWithMessage(200, "Directory create success")
    }

    @GetMapping("/directory/{id}")
    fun showDirectory(@PathVariable id: Long): MutableMap<String, Any>{
        val directory: Directory? = dirService.find(id)
        return response.responseObj(directory)
    }

    @PatchMapping("/directory/{id}")
    fun updateDirectory(@PathVariable id: Long, @RequestBody body: DirectoryRequest): MutableMap<String, Any>{
        val directory: Directory? = dirService.updateName(id, body)
        return response.responseCodeWithMessage(200, "Directory update success")
    }

    @DeleteMapping("/directory/{id}")
    fun deleteDirectory(@PathVariable id: Long): MutableMap<String, Any> {
        val directory = dirService.delete(id)
        return response.responseCodeWithMessage(200, "Delete directory success")
    }

    @GetMapping("/directories")
    fun listAllDirectories(@RequestParam parentId: Long?): MutableMap<String, Any> {
        val directories: List<DirectoryDTO> = dirService.getAll(parentId)
        return response.responseObj(directories, directories.size.toLong())
    }
}