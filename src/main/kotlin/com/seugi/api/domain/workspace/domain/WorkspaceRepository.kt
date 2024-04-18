package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.CrudRepository

interface WorkspaceRepository: MongoRepository<WorkspaceEntity, Long> {
    fun findByStudentEqualsAndTeacherEquals(userId: Long) : List<WorkspaceEntity>
}