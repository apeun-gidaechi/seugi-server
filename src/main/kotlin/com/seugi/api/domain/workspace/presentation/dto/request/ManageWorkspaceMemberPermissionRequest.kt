package com.seugi.api.domain.workspace.presentation.dto.request

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import jakarta.validation.constraints.NotBlank

data class ManageWorkspaceMemberPermissionRequest(
    val memberId: Long = -1,
    @field:NotBlank(message = "워크스페이스 아이디는 빈 값일 수 없습니다.")
    val workspaceId: String = "",
    val workspaceRole: WorkspaceRole,
)
