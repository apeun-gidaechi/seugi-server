package com.seugi.api.domain.workspace.service

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.presentation.dto.request.*
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceInfoResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceMemberChartResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceResponse
import com.seugi.api.global.response.BaseResponse

interface WorkspaceService {

    fun createWorkspace(userId: Long, createWorkspaceRequest: CreateWorkspaceRequest): BaseResponse<String>
    fun findWorkspaceById(id: String): WorkspaceEntity
    fun getWorkspace(workspaceId: String, userId: Long): BaseResponse<WorkspaceResponse>
    fun getWorkspaces(userId: Long): BaseResponse<List<WorkspaceResponse>>
    fun getWorkspaceCode(userId: Long, workspaceId: String): BaseResponse<String>
    fun searchWorkspace(code: String): BaseResponse<WorkspaceInfoResponse>
    fun joinWorkspace(userId: Long, joinWorkspaceRequest: JoinWorkspaceRequest): BaseResponse<Unit>
    fun getWaitList(userId: Long, getWaitListRequest: GetWaitListRequest): BaseResponse<List<RetrieveMemberResponse>>
    fun addWaitListToWorkspaceMember(userId: Long, waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest): BaseResponse<Unit>
    fun cancelWaitListToWorkspaceMember(
        userId: Long,
        waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit>
    fun deleteWorkspace(userId: Long, workspaceId: String): BaseResponse<Unit>
    fun updateWorkspace(userId: Long, updateWorkspaceRequest: UpdateWorkspaceRequest): BaseResponse<Unit>
    fun getMyWaitList(userId: Long): BaseResponse<List<WorkspaceInfoResponse>>
    fun getWorkspaceMemberChart(workspaceId: String): BaseResponse<WorkspaceMemberChartResponse>
    fun getWorkspaceMemberList(workspaceId: String): BaseResponse<Set<RetrieveProfileResponse>>

}