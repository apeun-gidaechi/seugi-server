package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface WorkspaceRepository: MongoRepository<WorkspaceEntity, ObjectId> {
    fun findByStudentEqualsAndTeacherEquals(userId: Long) : List<WorkspaceEntity>
    fun findByWorkspaceCodeEquals(workspaceCode: String) : WorkspaceEntity?
    fun findByWorkspaceCodeExists(workspaceCode: String) : Boolean
}