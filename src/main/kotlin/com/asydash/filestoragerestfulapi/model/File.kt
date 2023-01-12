package com.asydash.filestoragerestfulapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "files")
data class File(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "file_name", unique = true, nullable = false)
    var fileName: String,
    @Column(name = "file_uri", unique = true, nullable = false)
    var fileUri: String,
    @Column(name = "file_type", nullable = false)
    var fileType: String,
    var size: Long? = 0,
    @Column(name = "directory_id")
    var directoryId: Long? = null,
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now()
)
