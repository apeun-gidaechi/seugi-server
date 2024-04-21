package com.seugi.api.domain.workspace.presentation.controller

import com.seugi.api.domain.workspace.presentation.dto.request.*
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceResponse
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/workspace")
class WorkspaceController(
    private val workspaceService: WorkspaceService
) {

    @PostMapping("/")
    fun createWorkspace(
        @GetAuthenticatedId userId: Long,
        @RequestBody createWorkspaceRequest: CreateWorkspaceRequest
    ): BaseResponse<Unit> {
        return workspaceService.createWorkspace(userId = userId, createWorkspaceRequest = createWorkspaceRequest)
    }

    @DeleteMapping("/{workspaceId}")
    fun deleteWorkspace(
        @GetAuthenticatedId userId: Long,
        @PathVariable workspaceId: String,
    ): BaseResponse<Unit> {
        return workspaceService.deleteWorkspace(userId = userId, workspaceId = workspaceId)
    }

    @GetMapping("/")
    fun getWorkspace(
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<WorkspaceResponse>> {
        return workspaceService.getWorkspace(userId = userId)
    }

    @GetMapping("/code/{workspaceId}")
    fun getWorkspaceCode(
        @GetAuthenticatedId userId: Long,
        @PathVariable workspaceId: String
    ): BaseResponse<String> {
        return workspaceService.getWorkspaceCode(userId = userId, workspaceId = workspaceId)
    }

    @GetMapping("/{code}")
    fun searchWorkspace(
        @PathVariable code: String
    ): BaseResponse<WorkspaceResponse> {
        return workspaceService.searchWorkspace(code = code)
    }

    @PostMapping("/join")
    fun joinWorkspace(
        @GetAuthenticatedId userId: Long,
        @RequestBody joinWorkspaceRequest: JoinWorkspaceRequest
    ): BaseResponse<Unit> {
        return workspaceService.joinWorkspace(userId = userId, joinWorkspaceRequest = joinWorkspaceRequest)
    }

    @PatchMapping("/add")
    fun addWaitListToWorkspaceMember(
        @GetAuthenticatedId userId: Long,
        @RequestBody waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest
    ): BaseResponse<Unit>{
        return workspaceService.addWaitListToWorkspaceMember(userId = userId, waitSetWorkspaceMemberRequest = waitSetWorkspaceMemberRequest)
    }

    @GetMapping("/wait-list")
    fun getWaitList(
        @GetAuthenticatedId userId: Long,
        @RequestBody getWaitListRequest: GetWaitListRequest
    ): BaseResponse<Set<Long>> {
        return workspaceService.getWaitList(userId = userId, getWaitListRequest = getWaitListRequest)
    }

    @PatchMapping("/")
    fun updateWorkspace(
        @GetAuthenticatedId userId: Long,
        @RequestBody updateWorkspaceRequest: UpdateWorkspaceRequest
    ): BaseResponse<Unit> {
        return workspaceService.updateWorkspace(userId = userId, updateWorkspaceRequest = updateWorkspaceRequest)
    }



}