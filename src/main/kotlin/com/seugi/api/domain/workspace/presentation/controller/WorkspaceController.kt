package com.seugi.api.domain.workspace.presentation.controller

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import com.seugi.api.domain.workspace.presentation.dto.request.*
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceInfoResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceMemberChartResponse
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

    @PostMapping(path = ["", "/"])
    fun createWorkspace(
        @GetAuthenticatedId userId: Long,
        @RequestBody createWorkspaceRequest: CreateWorkspaceRequest
    ): BaseResponse<String> {
        return workspaceService.createWorkspace(userId = userId, createWorkspaceRequest = createWorkspaceRequest)
    }

    @DeleteMapping("/{workspaceId}")
    fun deleteWorkspace(
        @GetAuthenticatedId userId: Long,
        @PathVariable workspaceId: String,
    ): BaseResponse<Unit> {
        return workspaceService.deleteWorkspace(userId = userId, workspaceId = workspaceId)
    }

    @GetMapping(path = ["", "/"])
    fun getWorkspaces(
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<WorkspaceResponse>> {
        return workspaceService.getWorkspaces(userId = userId)
    }

    @GetMapping("/{workspaceId}")
    fun getWorkspace(
        @PathVariable workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<WorkspaceResponse> {
        return workspaceService.getWorkspace(
            workspaceId = workspaceId,
            userId = userId
        )
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
        @PathVariable code: String,
    ): BaseResponse<WorkspaceInfoResponse> {
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
    ): BaseResponse<Unit> {
        return workspaceService.addWaitListToWorkspaceMember(
            userId = userId,
            waitSetWorkspaceMemberRequest = waitSetWorkspaceMemberRequest
        )
    }

    @GetMapping("/wait-list")
    fun getWaitList(
        @GetAuthenticatedId userId: Long,
        @RequestParam("workspace", defaultValue = "") workspaceId: String,
        @RequestParam("role", defaultValue = "STUDENT") role: String
    ): BaseResponse<Set<Long>> {
        return workspaceService.getWaitList(
            userId = userId,
            getWaitListRequest = GetWaitListRequest(workspaceId = workspaceId, role = WorkspaceRole.valueOf(role))
        )
    }

    @PatchMapping(path = ["", "/"])
    fun updateWorkspace(
        @GetAuthenticatedId userId: Long,
        @RequestBody updateWorkspaceRequest: UpdateWorkspaceRequest
    ): BaseResponse<Unit> {
        return workspaceService.updateWorkspace(userId = userId, updateWorkspaceRequest = updateWorkspaceRequest)
    }

    @GetMapping("/my/wait-list")
    fun getMyWaitList(
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<WorkspaceInfoResponse>> {
        return workspaceService.getMyWaitList(userId = userId)
    }

    @GetMapping("/members/chart")
    fun getWorkspaceMemberChart(
        @GetAuthenticatedId userId: Long,
        workspaceId: String
    ): BaseResponse<WorkspaceMemberChartResponse> {
        return workspaceService.getWorkspaceMemberChart(workspaceId)
    }

    @GetMapping("/members")
    fun getWorkspaceMemberList(
        @GetAuthenticatedId userId: Long,
        workspaceId: String
    ): BaseResponse<Set<RetrieveProfileResponse>> {
        return workspaceService.getWorkspaceMemberList(workspaceId)
    }

}