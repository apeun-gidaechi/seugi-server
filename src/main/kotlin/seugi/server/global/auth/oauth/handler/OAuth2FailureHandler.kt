package seugi.server.global.auth.oauth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2FailureHandler : SimpleUrlAuthenticationFailureHandler() {

    @Value("\${rest.base-url}") val baseUrl: String = ""

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        redirectStrategy.sendRedirect(request,response, makeRedirectUri())
    }

    private fun makeRedirectUri(): String {
        return UriComponentsBuilder
            .fromUriString("$baseUrl/oauth2/redirect")
            .queryParam("status", "500")
            .queryParam("message", "구글 인증 실패")
            .build()
            .toUriString()
    }
}