package com.seugi.api.domain.workspace.presentation.dto.response

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse

class WorkspaceMemberChartResponse (
    val teachers: MutableMap<String, List<RetrieveProfileResponse>> = HashMap(),
    val students: MutableMap<String, List<RetrieveProfileResponse>> = HashMap()
)