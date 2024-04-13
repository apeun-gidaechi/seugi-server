package seugi.server.domain.member.application.service

import com.fasterxml.jackson.databind.JsonNode
import jakarta.transaction.Transactional
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import seugi.server.domain.member.adapter.`in`.dto.OAuth2MemberDTO
import seugi.server.domain.member.application.exception.MemberErrorCode
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.application.model.value.*
import seugi.server.domain.member.port.`in`.OAuth2MemberUseCase
import seugi.server.domain.member.port.out.ExistMemberPort
import seugi.server.domain.member.port.out.LoadMemberPort
import seugi.server.domain.member.port.out.SaveMemberPort
import seugi.server.global.auth.jwt.JwtInfo
import seugi.server.global.auth.jwt.JwtUtils
import seugi.server.global.auth.oauth.OAuth2Properties
import seugi.server.global.exception.CustomException
import seugi.server.global.response.BaseResponse

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
                password = MemberPassword(""),
                birth = MemberBirth(""),
                profile = MemberProfile(),
                role = MemberRole("ROLE_USER"),
                loginId = MemberLoginId(user.get("provider").asText() + "_" + user.get("provider_id").asText()),
                provider = MemberProvider(user.get("provider").asText()),
                providerId = MemberProviderId(user.get("provider").asText())
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
            HttpStatus.OK.value(),
            true,
            "OK",
            "로그인 성공 !",
            jwtUtils.generate(member)
        )
    }

    override fun complete(dto: OAuth2MemberDTO): BaseResponse<Unit> {
        val member = loadMemberPort.loadMemberWithEmail(dto.email)

        if (
            member.name.value.isNotBlank() &&
            member.birth.value.isNotBlank()
            ) {
            throw CustomException(MemberErrorCode.MEMBER_ALREADY_SUFFICIENT)
        }

        member.name = MemberName(dto.name)
        member.birth = MemberBirth(dto.birth)

        saveMemberPort.saveMember(
            member
        )

        return BaseResponse (
            HttpStatus.OK.value(),
            true,
            "OK",
            "회원가입 완료 ~ !!",
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