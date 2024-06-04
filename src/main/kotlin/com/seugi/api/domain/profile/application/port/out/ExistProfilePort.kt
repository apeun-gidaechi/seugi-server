package com.seugi.api.domain.profile.application.port.out

interface ExistProfilePort {

    fun existProfile(memberId: Long, workspaceId: String): Boolean

}