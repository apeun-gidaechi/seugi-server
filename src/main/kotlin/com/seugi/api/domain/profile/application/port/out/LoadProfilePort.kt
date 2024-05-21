package com.seugi.api.domain.profile.application.port.out

import com.seugi.api.domain.profile.application.model.Profile

interface LoadProfilePort {

    fun loadProfile(memberId: Long, workspaceId: String): Profile

}