package com.seugi.api.domain.workspace.domain.model

data class Workspace (
    val workspaceId: Long? = null,
    val workspaceName: String? = null,
    val workspaceImageUrl: String? = null,
    val workspaceAdmin: Long? = null,
    val workspaceCode: String? = null,
)