package com.seugi.api.domain.workspace.domain.mapper

import com.seugi.api.domain.workspace.domain.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.global.common.Mapper

class WorkspaceMapper: Mapper<Workspace, WorkspaceEntity> {

    override fun toDomain(entity: WorkspaceEntity): Workspace {
        return Workspace(
            workspaceId = entity.workspaceId,
            workspaceName = entity.workspaceName,
            workspaceImageUrl = entity.workspaceImageUrl,
            workspaceAdmin = entity.workspaceAdmin,
        )
    }

    override fun toEntity(domain: Workspace): WorkspaceEntity {
        return WorkspaceEntity(
            workspaceName = domain.workspaceName,
            workspaceImageUrl = domain.workspaceImageUrl,
            workspaceAdmin = domain.workspaceAdmin
        )
    }

}