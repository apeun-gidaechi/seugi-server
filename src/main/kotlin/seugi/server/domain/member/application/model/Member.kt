package seugi.server.domain.member.application.model

import seugi.server.domain.member.application.model.value.*

data class Member (

    val id: MemberId?,
    val name: MemberName,
    val email: MemberEmail,
    val password: MemberPassword,
    val birth: MemberBirth,
    val role: MemberRole,
    val loginId: MemberLoginId,
    val provider: MemberProvider,
    val providerId: MemberProviderId

)