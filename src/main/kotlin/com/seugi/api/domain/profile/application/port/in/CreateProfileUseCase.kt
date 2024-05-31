package com.seugi.api.domain.profile.application.port.`in`

interface CreateProfileUseCase {

    fun createProfile(memberId: Long, workspaceId: String)

}