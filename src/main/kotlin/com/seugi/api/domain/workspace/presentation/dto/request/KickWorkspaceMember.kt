package com.seugi.api.domain.workspace.presentation.dto.request

data class KickWorkspaceMember(
    val memberList: Set<Long>? = null,
    val workspaceId: String? = null,
)
