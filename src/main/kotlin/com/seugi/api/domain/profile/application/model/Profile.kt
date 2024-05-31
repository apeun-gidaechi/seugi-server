package com.seugi.api.domain.profile.application.model

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileRequest
import com.seugi.api.domain.profile.application.model.value.*

data class Profile (

    val id: ProfileId,
    var memberId: Member,
    var workspaceId : ProfileWorkspaceId,
    var status: ProfileStatus, // 상태메시지
    var nick: ProfileNick, // 닉네임
    var spot: ProfileSpot, // 직위
    var belong: ProfileBelong, // 소속
    var phone: ProfilePhone, // 휴대전화번호
    var wire: ProfileWire, // 유선전화번호
    var location: ProfileLocation // 근무위치

) {

    constructor (member: Member, workspaceId: String) : this (
        id = ProfileId(0),
        memberId = member,
        workspaceId = ProfileWorkspaceId(workspaceId),
        status = ProfileStatus(""),
        nick = ProfileNick(""),
        spot = ProfileSpot(""),
        belong = ProfileBelong(""),
        phone = ProfilePhone(""),
        wire = ProfileWire(""),
        location = ProfileLocation("")
    )

    fun editProfile(dto: EditProfileRequest) {
        this.status = ProfileStatus(dto.status)
        this.nick = ProfileNick(dto.nick)
        this.spot = ProfileSpot(dto.spot)
        this.belong = ProfileBelong(dto.belong)
        this.phone = ProfilePhone(dto.phone)
        this.wire = ProfileWire(dto.wire)
        this.location = ProfileLocation(dto.location)
    }

}