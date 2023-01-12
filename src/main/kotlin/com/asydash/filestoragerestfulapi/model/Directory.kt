package com.asydash.filestoragerestfulapi.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "directories")
data class Directory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "parent_id")
    var parentId: Long? = null,
    @Column(unique = true, nullable = false)
    var name: String,
    var size: Double? = 0.0,
    var contains: Int? = 0,
    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime? = LocalDateTime.now()
)
