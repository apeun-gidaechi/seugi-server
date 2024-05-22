package com.seugi.api.domain.profile.application.port.out

import com.seugi.api.domain.profile.application.model.Profile

interface SaveProfilePort {

    fun saveProfile(profile: Profile)

}