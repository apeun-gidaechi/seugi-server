package com.seugi.api.domain.workspace.presentation.controller

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import com.seugi.api.domain.workspace.presentation.dto.request.*
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceInfoResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceMemberChartResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceResponse
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/workspace")
class WorkspaceController(
    private val workspaceService: WorkspaceService,
) {

    @PostMapping(path = ["", "/"])
    fun createWorkspace(
        @GetAuthenticatedId userId: Long,
        @Valid @RequestBody createWorkspaceRequest: CreateWorkspaceRequest,
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
        @PathVariable workspaceId: String,
    ): BaseResponse<String> {
        return workspaceService.getWorkspaceCode(userId = userId, workspaceId = workspaceId)
    }

    @GetMapping("/search/{code}")
    fun searchWorkspace(
        @PathVariable code: String,
    ): BaseResponse<WorkspaceInfoResponse> {
        return workspaceService.searchWorkspace(code = code)
    }

    @PostMapping("/join")
    fun joinWorkspace(
        @GetAuthenticatedId userId: Long,
        @RequestBody joinWorkspaceRequest: JoinWorkspaceRequest,
    ): BaseResponse<Unit> {
        return workspaceService.joinWorkspace(userId = userId, joinWorkspaceRequest = joinWorkspaceRequest)
    }

    @PatchMapping("/add")
    fun addWaitListToWorkspaceMember(
        @GetAuthenticatedId userId: Long,
        @RequestBody waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit> {
        return workspaceService.addWaitListToWorkspaceMember(
            userId = userId,
            waitSetWorkspaceMemberRequest = waitSetWorkspaceMemberRequest
        )
    }

    @DeleteMapping("/cancel")
    fun cancelWaitListToWorkspaceMember(
        @GetAuthenticatedId userId: Long,
        @RequestBody waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit> {
        return workspaceService.cancelWaitListToWorkspaceMember(userId, waitSetWorkspaceMemberRequest)
    }

    @GetMapping("/wait-list")
    fun getWaitList(
        @GetAuthenticatedId userId: Long,
        @RequestParam("workspaceId", defaultValue = "") workspaceId: String,
        @RequestParam("role", defaultValue = "STUDENT") role: String,
    ): BaseResponse<List<RetrieveMemberResponse>> {
        return workspaceService.getWaitList(
            userId = userId,
            getWaitListRequest = GetWaitListRequest(workspaceId = workspaceId, role = WorkspaceRole.valueOf(role))
        )
    }

    @PatchMapping(path = ["", "/"])
    fun updateWorkspace(
        @GetAuthenticatedId userId: Long,
        @Valid @RequestBody updateWorkspaceRequest: UpdateWorkspaceRequest,
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
        @RequestParam workspaceId: String,
    ): BaseResponse<WorkspaceMemberChartResponse> {
        return workspaceService.getWorkspaceMemberChart(userId, workspaceId)
    }

    @GetMapping("/members")
    fun getWorkspaceMemberList(
        @GetAuthenticatedId userId: Long,
        @RequestParam workspaceId: String,
    ): BaseResponse<Set<RetrieveProfileResponse>> {
        return workspaceService.getWorkspaceMemberList(userId, workspaceId)
    }

    @PatchMapping("/permission")
    fun manageWorkspaceMemberPermission(
        @GetAuthenticatedId userId: Long,
        @Valid @RequestBody manageWorkspaceMemberPermissionRequest: ManageWorkspaceMemberPermissionRequest,
    ): BaseResponse<Unit> {
        return workspaceService.manageWorkspaceMemberPermission(userId, manageWorkspaceMemberPermissionRequest)
    }

}