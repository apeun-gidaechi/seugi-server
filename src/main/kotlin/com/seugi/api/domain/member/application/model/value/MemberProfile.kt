package com.seugi.api.domain.member.application.model.value

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity

class MemberProfile (

    val id: Long, // 프로필 ID
    val memberId: MemberEntity,
    val workspaceId : String, // 워크스페이스 ID
    var status: String = "", // 상태메시지
    var nick: String = "", // 닉네임
    var spot: String = "", // 직위
    var belong: String = "", // 소속
    var phone: String = "", // 휴대전화번호
    var wire: String = "", // 유선전화번호
    var location: String = "", // 근무위치

)