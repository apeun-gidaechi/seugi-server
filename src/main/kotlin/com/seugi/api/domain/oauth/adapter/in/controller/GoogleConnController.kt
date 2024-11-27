package com.seugi.api.domain.oauth.adapter.`in`.controller

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.oauth.adapter.`in`.dto.request.GoogleCodeRequest
import com.seugi.api.domain.oauth.port.`in`.GoogleConnUseCase
import com.seugi.api.global.common.annotation.GetResolvedMember
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
    fun connect(@GetResolvedMember model: Member, @RequestBody req: GoogleCodeRequest): BaseResponse<Unit> {
        return service.connect(model.id, req)
    }

}