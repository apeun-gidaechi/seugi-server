package com.seugi.api.domain.member.application.model.value

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity


class MemberProfile (

    val id: Long, // 프로필 ID
    val memberId: MemberEntity,
    val workspaceId : String, // 워크스페이스 ID
    val status: String = "", // 상태메시지
    val nick: String = "", // 닉네임
    val spot: String = "", // 직위
    val belong: String = "", // 소속
    val phone: String = "", // 휴대전화번호
    val wire: String = "", // 유선전화번호
    val location: String = "", // 근무위치

)