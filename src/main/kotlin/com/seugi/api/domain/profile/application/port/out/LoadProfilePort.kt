package com.seugi.api.domain.profile.application.port.out

import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity

interface LoadProfilePort {

    fun loadProfile(memberId: Long, workspaceId: String): ProfileEntity

}