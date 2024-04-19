package com.seugi.api.domain.workspace.presentation.controller

import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspaceRequest
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

    @GetMapping("/")
    fun getWorkspace(
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Workspace>> {
        return workspaceService.getWorkspace(userId = userId)
    }

    @PostMapping("/{code}")
    fun searchWorkspace(
        @PathVariable code: String
    ): BaseResponse<Workspace> {
        return workspaceService.searchWorkspace(code = code)
    }



}