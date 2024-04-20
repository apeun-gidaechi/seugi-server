package com.seugi.api.domain.workspace.domain.mapper

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspaceRequest
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceResponse
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class WorkspaceMapper: Mapper<Workspace, WorkspaceEntity> {

    override fun toDomain(entity: WorkspaceEntity): Workspace {
        return Workspace(
            workspaceId = entity.id.toString(),
            workspaceName = entity.workspaceName,
            workspaceImageUrl = entity.workspaceImageUrl,
            workspaceAdmin = entity.workspaceAdmin,
            middleAdmin = entity.middleAdmin,
            middleAdminWaitList = entity.middleAdminWaitList,
            studentWaitList = entity.studentWaitList,
            teacherWaitList = entity.teacherWaitList,
            student = entity.student,
            teacher = entity.teacher,
            workspaceCode = entity.workspaceCode,
            workspaceStatus = entity.status
        )
    }

    override fun toEntity(domain: Workspace): WorkspaceEntity {
        return WorkspaceEntity(
            workspaceName = domain.workspaceName,
            workspaceImageUrl = domain.workspaceImageUrl,
            workspaceAdmin = domain.workspaceAdmin,
            middleAdmin = domain.middleAdmin,
            middleAdminWaitList = domain.middleAdminWaitList,
            studentWaitList = domain.studentWaitList,
            teacherWaitList = domain.teacherWaitList,
            student = domain.student,
            teacher = domain.teacher,
            workspaceCode = domain.workspaceCode,
            status = domain.workspaceStatus
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

    fun toWorkspaceResponse(entity: WorkspaceEntity): WorkspaceResponse {
        return WorkspaceResponse(
            workspaceId = entity.id.toString(),
            workspaceName = entity.workspaceName!!,
            workspaceImageUrl = entity.workspaceImageUrl!!,
            studentCount = entity.student.size,
            teacherCount = entity.teacher.size+entity.middleAdmin.size+1 // 선생님, 중간관리자, 관리자 수 == 총 선생님 수,
        )
    }


}