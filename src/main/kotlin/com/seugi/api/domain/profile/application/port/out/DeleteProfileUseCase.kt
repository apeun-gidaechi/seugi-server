package com.seugi.api.domain.profile.application.port.out

interface DeleteProfileUseCase {
    fun deleteProfile(memberId: Long, workspaceId: String)
}