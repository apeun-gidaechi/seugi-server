package com.seugi.api.domain.workspace.domain.mapper

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspace
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
            student = domain.student,
            teacher = domain.teacher,
            workspaceCode = domain.workspaceCode
        )
    }

    fun toWorkspace(createWorkspace: CreateWorkspace, userId: Long, workspaceCode: String): Workspace {
        return Workspace(
            workspaceName = createWorkspace.workspaceName,
            workspaceImageUrl = createWorkspace.workspaceImageUrl,
            workspaceAdmin = userId,
            workspaceCode = workspaceCode
        )
    }


}