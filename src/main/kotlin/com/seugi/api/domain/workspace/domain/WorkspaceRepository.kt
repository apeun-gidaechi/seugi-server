package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.enums.Status
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface WorkspaceRepository: MongoRepository<WorkspaceEntity, ObjectId> {

    fun findByStatusAndStudentEqualsOrTeacherEqualsOrMiddleAdminEqualsOrWorkspaceAdminEquals(
        status: Status,
        student: MutableSet<Long>,
        teacher: MutableSet<Long>,
        middleAdmin: MutableSet<Long>,
        workspaceAdmin: Long
    ): List<WorkspaceEntity>
    fun findByWorkspaceCodeEquals(workspaceCode: String) : WorkspaceEntity?
    fun existsByWorkspaceCode(workspaceCode: String) : Boolean
}