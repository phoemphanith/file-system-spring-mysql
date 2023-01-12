package com.asydash.filestoragerestfulapi

import com.asydash.filestoragerestfulapi.config.property.FileStorageProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.io.IOException

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties::class)
class FileStorageRestfulApiApplication

fun main(args: Array<String>) {
	runApplication<FileStorageRestfulApiApplication>(*args)
}
