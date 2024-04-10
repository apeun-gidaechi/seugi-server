package seugi.server.global.auth.oauth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URI

@Component
class OAuth2Properties (

    @Value("\${oauth2.google.client-id}") val clientId: String,
    @Value("\${oauth2.google.client-secret}") val clientSecret: String,
    @Value("\${oauth2.google.redirect-uri}}") val redirectURI: String,
    @Value("\${oauth2.google.token-uri}") val tokenURI: String,
    @Value("\${oauth2.google.resource-uri}") val resourceURI: String

)