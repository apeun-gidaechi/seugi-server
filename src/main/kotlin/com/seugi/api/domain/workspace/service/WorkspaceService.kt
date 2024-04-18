package com.seugi.api.domain.workspace.service

import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspace
import com.seugi.api.global.response.BaseResponse

interface WorkspaceService {

    fun createWorkspace(userId: Long, createWorkspace: CreateWorkspace): BaseResponse<Unit>

}