package com.seugi.api.domain.task.service

import com.seugi.api.domain.task.domain.model.Task
import com.seugi.api.domain.task.domain.model.ClassroomTask
import com.seugi.api.domain.task.presentation.dto.request.CreateTaskRequest

interface TaskService {

    fun createTask(dto: CreateTaskRequest)
    fun getAllTask(workspaceId: String): List<Task>
    suspend fun getAllClassroomTask(userId: Long): List<ClassroomTask>

}