package com.seugi.api.domain.workspace.presentation.dto.response

data class WorkspaceResponse(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val studentCount: Int,
    val teacherCount: Int
)
