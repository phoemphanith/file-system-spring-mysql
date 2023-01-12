package com.asydash.filestoragerestfulapi.controller

import com.asydash.filestoragerestfulapi.payload.dto.DirectoryDTO
import com.asydash.filestoragerestfulapi.payload.dto.FileDTO
import com.asydash.filestoragerestfulapi.payload.response.ResponseObjectMap
import com.asydash.filestoragerestfulapi.service.FileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/api/file-storage")
class FileController {
    @Autowired
    lateinit var service: FileStorageService
    @Autowired
    lateinit var response: ResponseObjectMap

    @PostMapping("/upload-file")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("directory_id") directoryId: Long?): MutableMap<String, Any>{
        val fileSave = service.save(file, directoryId)
        return response.responseObj(fileSave)
    }

    @GetMapping("/files")
    fun listAll(@RequestParam("dir") directoryId: Long?): MutableMap<String, Any>{
        val files: List<FileDTO> = service.getAll(directoryId)
        return response.responseObj(files, files.size.toLong())
    }

    @PatchMapping("/file/{id}")
    fun updateFile(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("directory_id") directoryId: Long?): MutableMap<String, Any>{
        val fileUpdate = service.update(id, file, directoryId)
        return response.responseObj(fileUpdate)
    }

    @DeleteMapping("/file/{id}")
    fun deleteFile(@PathVariable id: Long): MutableMap<String, Any>{
        val fileDelete = service.delete(id)
        return response.responseCodeWithMessage(200, "File delete successfully")
    }

}