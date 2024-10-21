package com.seugi.api.domain.task.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<TaskEntity, Long> {

    fun findByWorkspaceId(workspaceId: String): List<TaskEntity>

}