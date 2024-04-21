package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.enums.Status
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface WorkspaceRepository: MongoRepository<WorkspaceEntity, ObjectId> {

    fun findByStudentEqualsOrTeacherEqualsOrMiddleAdminEqualsOrWorkspaceAdminEqualsAndStatus(
        student: Long,
        teacher: Long,
        middleAdmin: Long,
        workspaceAdmin: Long,
        status: Status
    ): List<WorkspaceEntity>
    fun findByWorkspaceCodeEquals(workspaceCode: String) : WorkspaceEntity?
    fun existsByWorkspaceCode(workspaceCode: String) : Boolean
}