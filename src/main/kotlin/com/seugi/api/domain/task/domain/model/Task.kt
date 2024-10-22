package com.seugi.api.domain.task.domain.model

import com.seugi.api.domain.task.presentation.dto.request.CreateTaskRequest
import java.time.LocalDateTime

data class Task (

    val id: Long,
    val workspaceId: String,
    val title: String,
    val description: String?,
    val dueDate: LocalDateTime?,

) {

    constructor(dto: CreateTaskRequest): this (
        id = 0,
        workspaceId = dto.workspaceId,
        title = dto.title,
        description = dto.description,
        dueDate = dto.dueDate
    )

}