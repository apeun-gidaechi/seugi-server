package com.seugi.api.domain.workspace.domain.mapper

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspaceRequest
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class WorkspaceMapper: Mapper<Workspace, WorkspaceEntity> {

    override fun toDomain(entity: WorkspaceEntity): Workspace {
        return Workspace(
            workspaceId = entity.workspaceId,
            workspaceName = entity.workspaceName,
            workspaceImageUrl = entity.workspaceImageUrl,
            workspaceAdmin = entity.workspaceAdmin,
            studentWaitList = entity.studentWaitList,
            teacherWaitList = entity.teacherWaitList,
            student = entity.student,
            teacher = entity.teacher,
            workspaceCode = entity.workspaceCode,
        )
    }

    override fun toEntity(domain: Workspace): WorkspaceEntity {
        return WorkspaceEntity(
            workspaceName = domain.workspaceName,
            workspaceImageUrl = domain.workspaceImageUrl,
            workspaceAdmin = domain.workspaceAdmin,
            studentWaitList = domain.studentWaitList,
            teacherWaitList = domain.teacherWaitList,
            student = domain.student,
            teacher = domain.teacher,
            workspaceCode = domain.workspaceCode
        )
    }

    fun toWorkspace(createWorkspaceRequest: CreateWorkspaceRequest, userId: Long, workspaceCode: String): Workspace {
        return Workspace(
            workspaceName = createWorkspaceRequest.workspaceName,
            workspaceImageUrl = createWorkspaceRequest.workspaceImageUrl,
            workspaceAdmin = userId,
            workspaceCode = workspaceCode
        )
    }


}