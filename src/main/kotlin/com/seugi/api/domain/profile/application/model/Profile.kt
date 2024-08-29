package com.seugi.api.domain.profile.application.model

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileRequest
import com.seugi.api.domain.profile.adapter.`in`.request.EditSchIdNumRequest
import com.seugi.api.domain.profile.application.model.value.*

data class Profile (

    val id: ProfileId = ProfileId(0),
    var memberId: Member,
    var workspaceId : ProfileWorkspaceId,
    var schGrade: ProfileSchGrade = ProfileSchGrade(0),
    var schClass: ProfileSchClass = ProfileSchClass(0),
    var schNumber: ProfileSchNumber = ProfileSchNumber(0),
    var status: ProfileStatus = ProfileStatus(""), // 상태메시지
    var nick: ProfileNick = ProfileNick(""), // 닉네임
    var spot: ProfileSpot = ProfileSpot(""), // 직위
    var belong: ProfileBelong = ProfileBelong(""), // 소속
    var phone: ProfilePhone = ProfilePhone(""), // 휴대전화번호
    var wire: ProfileWire = ProfileWire(""), // 유선전화번호
    var location: ProfileLocation = ProfileLocation("") // 근무위치

) {

    fun editProfile(dto: EditProfileRequest) {
        this.status = ProfileStatus(dto.status)
        this.nick = ProfileNick(dto.nick)
        this.spot = ProfileSpot(dto.spot)
        this.belong = ProfileBelong(dto.belong)
        this.phone = ProfilePhone(dto.phone)
        this.wire = ProfileWire(dto.wire)
        this.location = ProfileLocation(dto.location)
    }

    fun editSchIdNum(dto: EditSchIdNumRequest) {
        this.schGrade = ProfileSchGrade(dto.schGrade)
        this.schClass = ProfileSchClass(dto.schClass)
        this.schNumber = ProfileSchNumber(dto.schNumber)
    }

}