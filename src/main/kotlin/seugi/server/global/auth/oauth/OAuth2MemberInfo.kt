package seugi.server.global.auth.oauth

interface OAuth2MemberInfo {

    val email: String
    val name: String
    val provider: String
    val providerId: String

}