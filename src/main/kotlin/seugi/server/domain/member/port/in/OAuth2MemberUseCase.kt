package seugi.server.domain.member.port.`in`

interface OAuth2MemberUseCase {

    fun process(code: String, registrationId: String)

    fun getAccessToken(code: String): String

}