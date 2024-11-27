package com.seugi.api.domain.workspace.presentation.controller

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import com.seugi.api.domain.workspace.presentation.dto.request.*
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceInfoResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceMemberChartResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceResponse
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.common.annotation.GetResolvedMember
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
        @GetResolvedMember model: Member,
        @Valid @RequestBody createWorkspaceRequest: CreateWorkspaceRequest,
    ): BaseResponse<String> {
        return workspaceService.createWorkspace(userId = model.id, createWorkspaceRequest = createWorkspaceRequest)
    }

    @DeleteMapping("/{workspaceId}")
    fun deleteWorkspace(
        @GetResolvedMember model: Member,
        @PathVariable workspaceId: String,
    ): BaseResponse<Unit> {
        return workspaceService.deleteWorkspace(userId = model.id, workspaceId = workspaceId)
    }

    @GetMapping(path = ["", "/"])
    fun getWorkspaces(
        @GetResolvedMember model: Member,
    ): BaseResponse<List<WorkspaceResponse>> {
        return workspaceService.getWorkspaces(userId = model.id)
    }

    @GetMapping("/{workspaceId}")
    fun getWorkspace(
        @PathVariable workspaceId: String,
        @GetResolvedMember model: Member,
    ): BaseResponse<WorkspaceResponse> {
        return workspaceService.getWorkspace(
            workspaceId = workspaceId,
            userId = model.id
        )
    }

    @GetMapping("/code/{workspaceId}")
    fun getWorkspaceCode(
        @GetResolvedMember model: Member,
        @PathVariable workspaceId: String,
    ): BaseResponse<String> {
        return workspaceService.getWorkspaceCode(userId = model.id, workspaceId = workspaceId)
    }

    @GetMapping("/search/{code}")
    fun searchWorkspace(
        @PathVariable code: String,
    ): BaseResponse<WorkspaceInfoResponse> {
        return workspaceService.searchWorkspace(code = code)
    }

    @PostMapping("/join")
    fun joinWorkspace(
        @GetResolvedMember model: Member,
        @RequestBody joinWorkspaceRequest: JoinWorkspaceRequest,
    ): BaseResponse<Unit> {
        return workspaceService.joinWorkspace(userId = model.id, joinWorkspaceRequest = joinWorkspaceRequest)
    }

    @PatchMapping("/add")
    fun addWaitListToWorkspaceMember(
        @GetResolvedMember model: Member,
        @RequestBody waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit> {
        return workspaceService.addWaitListToWorkspaceMember(
            userId = model.id,
            waitSetWorkspaceMemberRequest = waitSetWorkspaceMemberRequest
        )
    }

    @DeleteMapping("/cancel")
    fun cancelWaitListToWorkspaceMember(
        @GetResolvedMember model: Member,
        @RequestBody waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit> {
        return workspaceService.cancelWaitListToWorkspaceMember(model.id, waitSetWorkspaceMemberRequest)
    }

    @GetMapping("/wait-list")
    fun getWaitList(
        @GetResolvedMember model: Member,
        @RequestParam("workspaceId", defaultValue = "") workspaceId: String,
        @RequestParam("role", defaultValue = "STUDENT") role: String,
    ): BaseResponse<List<RetrieveMemberResponse>> {
        return workspaceService.getWaitList(
            userId = model.id,
            getWaitListRequest = GetWaitListRequest(workspaceId = workspaceId, role = WorkspaceRole.valueOf(role))
        )
    }

    @PatchMapping(path = ["", "/"])
    fun updateWorkspace(
        @GetResolvedMember model: Member,
        @Valid @RequestBody updateWorkspaceRequest: UpdateWorkspaceRequest,
    ): BaseResponse<Unit> {
        return workspaceService.updateWorkspace(userId = model.id, updateWorkspaceRequest = updateWorkspaceRequest)
    }

    @GetMapping("/my/wait-list")
    fun getMyWaitList(
        @GetResolvedMember model: Member,
    ): BaseResponse<List<WorkspaceInfoResponse>> {
        return workspaceService.getMyWaitList(userId = model.id)
    }

    @GetMapping("/members/chart")
    fun getWorkspaceMemberChart(
        @GetResolvedMember model: Member,
        @RequestParam workspaceId: String,
    ): BaseResponse<WorkspaceMemberChartResponse> {
        return workspaceService.getWorkspaceMemberChart(model.id, workspaceId)
    }

    @GetMapping("/members")
    fun getWorkspaceMemberList(
        @GetResolvedMember model: Member,
        @RequestParam workspaceId: String,
    ): BaseResponse<Set<RetrieveProfileResponse>> {
        return workspaceService.getWorkspaceMemberList(model.id, workspaceId)
    }

    @PatchMapping("/permission")
    fun manageWorkspaceMemberPermission(
        @GetResolvedMember model: Member,
        @Valid @RequestBody manageWorkspaceMemberPermissionRequest: ManageWorkspaceMemberPermissionRequest,
    ): BaseResponse<Unit> {
        return workspaceService.manageWorkspaceMemberPermission(model.id, manageWorkspaceMemberPermissionRequest)
    }

    @PatchMapping("/kick")
    fun kickWorkspaceMember(
        @GetResolvedMember model: Member,
        @RequestBody kickWorkspaceMemberRequest: KickWorkspaceMemberRequest,
    ): BaseResponse<Unit> {
        return workspaceService.kickWorkspaceMember(model.id, kickWorkspaceMemberRequest)
    }

}