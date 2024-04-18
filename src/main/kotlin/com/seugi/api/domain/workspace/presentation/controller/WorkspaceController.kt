package com.seugi.api.domain.workspace.presentation.controller

import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspace
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
        @RequestBody createWorkspace: CreateWorkspace
    ): BaseResponse<Unit> {
        return workspaceService.createWorkspace(userId = userId, createWorkspace = createWorkspace)
    }

    @GetMapping("/")
    fun getWorkspace(
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Workspace>> {
        return workspaceService.getWorkspace(userId = userId)
    }



}