package seugi.server.domain.member.application.service

import com.fasterxml.jackson.databind.JsonNode
import jakarta.transaction.Transactional
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import seugi.server.domain.member.port.`in`.OAuth2MemberUseCase
import seugi.server.global.auth.oauth.OAuth2Properties

@Service
@Transactional
class OAuth2MemberService (
    private val oAuth2Properties: OAuth2Properties,
    private val restTemplate: RestTemplate
) : OAuth2MemberUseCase {

    override fun process(code: String, registrationId: String) {
        val token = getAccessToken(code)

        println(token)
    }

    override fun getAccessToken(code: String): String {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("client_id", oAuth2Properties.clientId)
        params.add("client_secret", oAuth2Properties.clientSecret)
        params.add("redirect_uri", oAuth2Properties.redirectURI)
        params.add("grant_type", "authorization_code")

        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val entity: HttpEntity<*> = HttpEntity<Any?>(params, headers)

        val responseNode: ResponseEntity<JsonNode> = restTemplate.exchange(
            oAuth2Properties.tokenURI,
            HttpMethod.POST,
            entity,
            JsonNode::class.java
        )

        val accessTokenNode = responseNode.body

        return accessTokenNode!!.get("access_token").asText()
    }

    override fun getUserResource(token: String): JsonNode {
        val headers = HttpHeaders()

        headers.set("Authorization", "Bearer $token")

        val entity = HttpEntity<Unit>(headers)

        return restTemplate.exchange(
            oAuth2Properties.resourceURI,
            HttpMethod.GET,
            entity,
            JsonNode::class.java
        ).body!!
    }
}