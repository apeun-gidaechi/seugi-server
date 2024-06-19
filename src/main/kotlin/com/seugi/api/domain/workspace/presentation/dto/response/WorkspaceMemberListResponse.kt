package com.seugi.api.domain.workspace.presentation.dto.response

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.profile.application.model.Profile

class WorkspaceMemberListResponse (
    val teachers: MutableMap<String, List<RetrieveProfileResponse>> = HashMap(),
    val students: MutableMap<String, List<RetrieveProfileResponse>> = HashMap()
)