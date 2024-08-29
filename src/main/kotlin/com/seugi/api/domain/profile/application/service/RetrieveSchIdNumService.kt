package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveSchIdNumResponse
import com.seugi.api.domain.profile.application.port.`in`.RetrieveSchIdNumUseCase
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import org.springframework.stereotype.Service

@Service
class RetrieveSchIdNumService (
    private val loadProfilePort: LoadProfilePort
) : RetrieveSchIdNumUseCase {

    override fun retrieveSchIdNum(workspaceId: String, id: Long): RetrieveSchIdNumResponse {
        val profile = loadProfilePort.loadProfile(id, workspaceId)

        return RetrieveSchIdNumResponse(
            profile.schGrade.value,
            profile.schClass.value,
            profile.schNumber.value
        )
    }
}