package seugi.server.domain.member.application.model.value

import jakarta.persistence.Embeddable

@Embeddable
data class MemberProfile (

    var status: String = "", // 상태메시지
    var nick: String = "", // 닉네임
    var spot: String = "", // 직위
    var belong: String = "", // 소속
    var phone: String = "", // 휴대전화번호
    var wire: String = "", // 유선전화번호
    var location: String = "", // 근무위치

)