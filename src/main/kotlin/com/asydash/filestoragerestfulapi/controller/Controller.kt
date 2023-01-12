package com.asydash.filestoragerestfulapi.controller

import com.asydash.filestoragerestfulapi.service.FileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.io.IOException

@RestController
class Controller {
    @Autowired
    lateinit var service: FileStorageService

    @RequestMapping(value = ["/uploads/{fileName:.+}"], method = [RequestMethod.GET], produces = [MediaType.IMAGE_JPEG_VALUE])
    @Throws(IOException::class)
    fun getImage(@PathVariable fileName: String): ResponseEntity<ByteArray?>? {
        val resource: UrlResource? = service.loadResource(fileName)
        val imgFile = resource?.inputStream
        val bytes = StreamUtils.copyToByteArray(imgFile)
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes)
    }
}