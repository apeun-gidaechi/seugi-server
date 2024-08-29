package com.seugi.api.domain.profile.application.port.`in`

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveSchIdNumResponse

interface RetrieveSchIdNumUseCase {

    fun retrieveSchIdNum(workspaceId: String, id: Long): RetrieveSchIdNumResponse

}