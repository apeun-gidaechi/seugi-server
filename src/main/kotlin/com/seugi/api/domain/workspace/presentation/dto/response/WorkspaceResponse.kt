package com.seugi.api.domain.workspace.presentation.dto.response

data class WorkspaceResponse(
    val workspaceId: String? = null,
    val workspaceName: String? = null,
    val workspaceImageUrl: String? = null,
    val workspaceAdmin: Long? = null,
    val middleAdmin: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val student: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val teacher: MutableSet<Long> = emptySet<Long>().toMutableSet(),
)
