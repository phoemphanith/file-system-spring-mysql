package com.asydash.filestoragerestfulapi.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "file")
class FileStorageProperties(var uploadDir: String)