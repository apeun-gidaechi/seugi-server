package com.seugi.api.domain.profile.application.model

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileRequest
import com.seugi.api.domain.profile.adapter.`in`.request.EditSchIdNumRequest
import com.seugi.api.domain.profile.application.model.value.*

data class Profile (

    val id: ProfileId = ProfileId(0),
    var member: Member,
    var workspaceId : ProfileWorkspaceId,
    var permission: ProfilePermission, // 워크스페이스 권한
    var schGrade: ProfileSchGrade = ProfileSchGrade(0), // 학년
    var schClass: ProfileSchClass = ProfileSchClass(0), // 반
    var schNumber: ProfileSchNumber = ProfileSchNumber(0), // 번호
    var status: ProfileStatus = ProfileStatus(""), // 상태메시지
    var nick: ProfileNick = ProfileNick(member.name.value), // 닉네임
    var spot: ProfileSpot = ProfileSpot(""), // 직위
    var belong: ProfileBelong = ProfileBelong(""), // 소속
    var phone: ProfilePhone = ProfilePhone(""), // 휴대전화번호
    var wire: ProfileWire = ProfileWire(""), // 유선전화번호
    var location: ProfileLocation = ProfileLocation(""), // 근무위치

) {

    fun changeRole(permission: ProfilePermission) {
        this.permission = permission
    }

    fun editProfile(dto: EditProfileRequest) {
        this.status = ProfileStatus(dto.status ?: this.status.value)
        this.nick = ProfileNick(dto.nick ?: this.nick.value)
        this.spot = ProfileSpot(dto.spot ?: this.spot.value)
        this.belong = ProfileBelong(dto.belong ?: this.belong.value)
        this.phone = ProfilePhone(dto.phone ?: this.phone.value)
        this.wire = ProfileWire(dto.wire ?: this.wire.value)
        this.location = ProfileLocation(dto.location ?: this.location.value)
    }

    fun editSchIdNum(dto: EditSchIdNumRequest) {
        this.schGrade = ProfileSchGrade(dto.schGrade)
        this.schClass = ProfileSchClass(dto.schClass)
        this.schNumber = ProfileSchNumber(dto.schNumber)
    }

}