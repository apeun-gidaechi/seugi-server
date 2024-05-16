package com.seugi.api.domain.profile.application.port.out

import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity

interface SaveProfilePort {

    fun saveProfile(profile: ProfileEntity)

}