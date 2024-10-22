package com.seugi.api.domain.task.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class TaskEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val workspaceId: String,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String?,

    @Column(nullable = false)
    val dueDate: LocalDateTime?,

)