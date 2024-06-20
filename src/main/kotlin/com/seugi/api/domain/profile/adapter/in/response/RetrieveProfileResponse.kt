package com.seugi.api.domain.profile.adapter.`in`.response

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.profile.application.model.Profile

data class RetrieveProfileResponse (

    val workspaceId: String, // 워크스페이스 ID
    val member: RetrieveMemberResponse,
    val status: String = "", // 상태메시지
    val nick: String = "", // 닉네임
    val spot: String = "", // 직위
    val belong: String = "", // 소속
    val phone: String = "", // 휴대전화번호
    val wire: String = "", // 유선전화번호
    val location: String = "", // 근무위치

) {

    constructor (profile: Profile) : this (
        workspaceId = profile.workspaceId.value,
        member = RetrieveMemberResponse(profile.memberId),
        status = profile.status.value,
        nick = profile.nick.value,
        spot = profile.spot.value,
        belong = profile.belong.value,
        phone = profile.phone.value,
        wire = profile.wire.value,
        location = profile.location.value
    )

}