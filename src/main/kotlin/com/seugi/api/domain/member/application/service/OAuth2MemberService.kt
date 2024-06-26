package com.seugi.api.domain.member.application.service

import com.fasterxml.jackson.databind.JsonNode
import com.seugi.api.domain.member.adapter.`in`.dto.req.OAuth2MemberRequest
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.model.value.*
import com.seugi.api.domain.member.application.port.`in`.OAuth2MemberUseCase
import com.seugi.api.domain.member.application.port.out.ExistMemberPort
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.oauth.OAuth2Properties
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import jakarta.transaction.Transactional
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class OAuth2MemberService (
    private val oAuth2Properties: OAuth2Properties,
    private val restTemplate: RestTemplate,
    private val existMemberPort: ExistMemberPort,
    private val saveMemberPort: SaveMemberPort,
    private val loadMemberPort: LoadMemberPort,
    private val jwtUtils: JwtUtils
) : OAuth2MemberUseCase {

    override fun process(code: String, provider: String): BaseResponse<JwtInfo> {
        val token = this.getAccessToken(code)

        val user = this.getUserResource(token)

        if (!existMemberPort.existMemberWithEmail(user.get("email").asText())) {
            val member = Member(
                id = null,
                name = MemberName(""),
                email = MemberEmail(user.get("email").asText()),
                picture = MemberPicture(user.get("profile_img").asText()),
                password = MemberPassword(""),
                birth = MemberBirth(""),
                role = MemberRole("ROLE_USER"),
                loginId = MemberLoginId(user.get("provider").asText() + "_" + user.get("provider_id").asText()),
                provider = MemberProvider(user.get("provider").asText()),
                providerId = MemberProviderId(user.get("provider").asText()),
                deleted = MemberDeleted(false)
            )

            saveMemberPort.saveMember(member)

            throw CustomException(MemberErrorCode.MEMBER_NOT_SUFFICIENT)
        }

        val member = loadMemberPort.loadMemberWithEmail(user.get("email").asText())

        if (
            member.name.value.isBlank() ||
            member.birth.value.isBlank()
            ) {
            throw CustomException(MemberErrorCode.MEMBER_NOT_SUFFICIENT)
        }

        return BaseResponse (
            message = "로그인 성공 !",
            data = jwtUtils.generate(member)
        )
    }

    override fun complete(dto: OAuth2MemberRequest): BaseResponse<Unit> {
        val member = loadMemberPort.loadMemberWithEmail(dto.email)

        if (member.name.value.isNotBlank()) {
            throw CustomException(MemberErrorCode.MEMBER_ALREADY_SUFFICIENT)
        }

        member.name = MemberName(dto.name)

        saveMemberPort.saveMember(
            member
        )

        return BaseResponse (
            message = "회원가입 완료 ~ !!",
        )
    }

    override fun getAccessToken(code: String): String {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("client_id", oAuth2Properties.clientId)
        params.add("client_secret", oAuth2Properties.clientSecret)
        params.add("redirect_uri", oAuth2Properties.redirectURI)
        params.add("grant_type", "authorization_code")

        val headers = HttpHeaders()
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