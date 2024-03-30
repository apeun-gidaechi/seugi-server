package seugi.server.global.auth.oauth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import seugi.server.domain.member.port.out.LoadMemberPort
import seugi.server.global.auth.jwt.JwtInfo
import seugi.server.global.auth.jwt.JwtUtils

@Component
class OAuth2SuccessfulHandler (
    private val jwtUtils: JwtUtils,
    private val loadMemberPort: LoadMemberPort
) : SimpleUrlAuthenticationSuccessHandler() {

    @Value("\${oauth2.redirect-url}") val redirectUrl: String = ""

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {

        val url: String = makeRedirectUri(
            jwtUtils.generate(
                loadMemberPort
                    .loadMemberWithEmail(
                        authentication.name
                    )
            )
        )

        return redirectStrategy.sendRedirect(request, response, url)

    }

    private fun makeRedirectUri(jwtInfo: JwtInfo): String {
        return UriComponentsBuilder
            .fromUriString(redirectUrl)
            .queryParam("accessToken", jwtInfo.accessToken)
            .queryParam("refreshToken", jwtInfo.refreshToken)
            .build()
            .toUriString()
    }
}