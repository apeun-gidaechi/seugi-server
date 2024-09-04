package com.seugi.api.domain.oauth.adapter.`in`.controller

import com.seugi.api.domain.oauth.adapter.`in`.dto.GoogleConnRequest
import com.seugi.api.domain.oauth.port.`in`.GoogleConnUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth/google")
class GoogleConnController (
    private val service: GoogleConnUseCase
) {

    @PostMapping("/connect")
    fun connect(@GetAuthenticatedId id: Long, @RequestBody req: GoogleConnRequest): BaseResponse<Unit> {
        return service.connect(id, req)
    }

}