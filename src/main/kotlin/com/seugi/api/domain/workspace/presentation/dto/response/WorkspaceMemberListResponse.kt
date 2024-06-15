package com.seugi.api.domain.workspace.presentation.dto.response

import com.seugi.api.domain.profile.application.model.Profile

class WorkspaceMemberListResponse (
    val teachers: MutableMap<String, List<Profile>> = HashMap(),
    val students: MutableMap<String, List<Profile>> = HashMap()
)