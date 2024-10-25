package com.seugi.api.domain.oauth.adapter.`in`.controller

import com.seugi.api.domain.oauth.port.`in`.GoogleRemoveUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth/google")
class GoogleRemoveController (
    private val service: GoogleRemoveUseCase
) {

    @DeleteMapping("/remove")
    fun remove(@GetAuthenticatedId userId: Long): BaseResponse<Unit> {
        service.remove(userId)
        return BaseResponse(message = "삭제 성공 !")
    }

}