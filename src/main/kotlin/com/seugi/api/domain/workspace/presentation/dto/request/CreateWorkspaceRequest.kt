package com.seugi.api.domain.workspace.presentation.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateWorkspaceRequest (
    @field:NotBlank(message = "워크스페이스 이름은 공백일 수 없습니다.")
    val workspaceName: String = "",
    val workspaceImageUrl: String = "",
)