package com.asydash.filestoragerestfulapi.service

import com.asydash.filestoragerestfulapi.config.property.FileStorageProperties
import com.asydash.filestoragerestfulapi.exception.FileNotFoundException
import com.asydash.filestoragerestfulapi.exception.FileStorageException
import com.asydash.filestoragerestfulapi.model.File
import com.asydash.filestoragerestfulapi.payload.dto.FileDTO
import com.asydash.filestoragerestfulapi.repository.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class FileStorageService {
    private var fileLocation: Path
    @Autowired
    lateinit var repository: FileRepository

    constructor(fileProperties: FileStorageProperties){
        this.fileLocation = Paths.get(fileProperties.uploadDir).toAbsolutePath().normalize()
        try {
            Files.createDirectories(fileLocation)
        }catch (ex: Exception){
            throw FileStorageException("Could not create the directory where the uploaded files will be stored.", ex)
        }
    }

    fun getAll(directoryId: Long?): List<FileDTO> = repository.findAllByDirectoryId(directoryId)
        .map { item: File ->
            FileDTO(
                id = item.id,
                fileName = item.fileName,
                fileUri = item.fileUri,
                fileType = item.fileType,
                size = item.size) }

    fun storeFile(file: MultipartFile, directoryId: Long?): String{
        var fileName: String = StringUtils.cleanPath(file.originalFilename!!)

        try {
            if(fileName.contains("..")) {
                throw FileStorageException("Sorry! Filename container invalid path sequence $fileName")
            }

            var localDateTime: Long = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli()
            val splitFileName = fileName.split(".") //[name,extension]
            val fileExtension = splitFileName[1]
            val hexFileName = DigestUtils.md5DigestAsHex(splitFileName[1].byteInputStream()).substring(0,16)
            fileName = "photo-$localDateTime-$hexFileName.$fileExtension"

            val targetLocation: Path = fileLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

            return fileName
        }catch (ex: IOException){
            throw FileStorageException("Could not store file $fileName. Please try again!", ex)
        }
    }

    fun save(file: MultipartFile, directoryId: Long?): File{
        val fileName = this.storeFile(file, directoryId)
        val newFile = File(
            fileName = fileName,
            fileUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString(),
            fileType = file.contentType.toString(),
            size = file.size,
            directoryId = directoryId)

        return repository.save(newFile)
    }

    fun update(id: Long, file: MultipartFile, newDir: Long?): File{
        val currentFile = repository.findById(id).orElse(null)
        val currentFileName = currentFile.fileName
        if(currentFile != null){

            val fileName = this.storeFile(file, newDir)
            val newFile = currentFile.copy(
                fileName = fileName,
                fileType = file.contentType.toString(),
                fileUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString(),
                directoryId = newDir,
                size = file.size
            )

            val filePath = fileLocation.resolve(currentFileName)
            Files.deleteIfExists(filePath)

            return repository.save(newFile)
        }

        throw FileNotFoundException("File not found")
    }

    fun loadResource(fileName: String): UrlResource{
        try {
            val filePath = fileLocation.resolve(fileName).normalize()
            val resource = UrlResource(filePath.toUri())
            if(resource.exists()){
                return resource
            }
            throw FileNotFoundException("File not found")
        }catch (ex: MalformedURLException){
            throw FileNotFoundException("File not found", ex)
        }
    }

    fun delete(id: Long): Boolean{
        try {
            val currentFile = repository.findById(id).orElse(null)
            if(currentFile != null){
                repository.deleteById(id)
                val filePath = fileLocation.resolve(currentFile.fileName)
                return Files.deleteIfExists(filePath)
            }
            throw FileNotFoundException("File not found")
        }catch (ex: IOException){
            throw FileNotFoundException("File not found", ex)
        }
    }
}