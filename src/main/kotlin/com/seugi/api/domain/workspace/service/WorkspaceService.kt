package com.seugi.api.domain.workspace.service

import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspaceRequest
import com.seugi.api.domain.workspace.presentation.dto.request.GetWaitListRequest
import com.seugi.api.domain.workspace.presentation.dto.request.JoinWorkspaceRequest
import com.seugi.api.domain.workspace.presentation.dto.request.WaitSetWorkspaceMemberRequest
import com.seugi.api.global.response.BaseResponse

interface WorkspaceService {

    fun createWorkspace(userId: Long, createWorkspaceRequest: CreateWorkspaceRequest): BaseResponse<Unit>
    fun getWorkspace(userId: Long) : BaseResponse<List<Workspace>>
    fun searchWorkspace(code: String): BaseResponse<Workspace>
    fun joinWorkspace(userId: Long, joinWorkspaceRequest: JoinWorkspaceRequest): BaseResponse<Unit>
    fun getWaitList(userId: Long, getWaitListRequest: GetWaitListRequest): BaseResponse<Set<Long>>
    fun addWaitListToWorkspaceMember(userId: Long, waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest): BaseResponse<Unit>
}