package seugi.server.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.member.port.`in`.OAuth2MemberUseCase

@RestController
@RequestMapping("/member")
class OAuth2MemberController (
    private val oAuth2MemberUseCase: OAuth2MemberUseCase
) {

    @GetMapping("/oauth2")
    fun socialLogin(code: String, registrationId: String) {
        oAuth2MemberUseCase.process(code, registrationId)
    }

}