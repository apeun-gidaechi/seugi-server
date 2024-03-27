package seugi.server.global.auth.oauth

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.application.model.value.*
import seugi.server.domain.member.port.out.ExistMemberPort
import seugi.server.domain.member.port.out.LoadMemberPort
import seugi.server.domain.member.port.out.SaveMemberPort

@Service
class OAuth2MemberService(
    private val existMemberPort: ExistMemberPort,
    private val saveMemberPort: SaveMemberPort,
    private val loadMemberPort: LoadMemberPort,
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId;

        val memberInfo = GoogleMemberInfo(oAuth2User.attributes)

        val member: Member

        if (!existMemberPort.existMemberWithEmail(memberInfo.email)) {
            member = Member (
                id = null,
                name = MemberName(memberInfo.name),
                email = MemberEmail(memberInfo.email),
                password = MemberPassword(""),
                birth = MemberBirth(""),
                role = MemberRole("ROLE_USER"),
                loginId = MemberLoginId(memberInfo.provider + "_" + memberInfo.providerId),
                provider = MemberProvider(memberInfo.provider),
                providerId = MemberProviderId(memberInfo.providerId)
            )

            saveMemberPort.saveMember(member)
        } else {
            member = loadMemberPort.loadMemberWithEmail(memberInfo.email)
        }

        return CustomOAuth2UserDetails(member, oAuth2User.attributes)
    }
}