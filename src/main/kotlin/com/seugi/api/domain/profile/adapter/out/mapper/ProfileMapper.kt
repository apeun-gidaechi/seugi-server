package com.seugi.api.domain.profile.adapter.out.mapper

import com.seugi.api.domain.member.adapter.out.mapper.MemberMapper
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.model.value.*
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class ProfileMapper (
    private val memberMapper: MemberMapper
) : Mapper<Profile, ProfileEntity> {

    override fun toDomain(entity: ProfileEntity): Profile {
        return Profile (
            belong = ProfileBelong(entity.belong),
            id = ProfileId(entity.id),
            location = ProfileLocation(entity.location),
            memberId = memberMapper.toDomain(entity.memberId),
            schGrade = ProfileSchGrade(entity.schGrade),
            schClass = ProfileSchClass(entity.schClass),
            schNumber = ProfileSchNumber(entity.schNumber),
            nick = ProfileNick(entity.nick),
            phone = ProfilePhone(entity.phone),
            spot = ProfileSpot(entity.spot),
            status = ProfileStatus(entity.status),
            wire = ProfileWire(entity.wire),
            workspaceId = ProfileWorkspaceId(entity.workspaceId)
        )
    }

    override fun toEntity(domain: Profile): ProfileEntity {
        return ProfileEntity (
            belong = domain.belong.value,
            id = domain.id.value,
            location = domain.location.value,
            memberId = memberMapper.toEntity(domain.memberId),
            schGrade = domain.schGrade.value,
            schClass = domain.schClass.value,
            schNumber = domain.schNumber.value,
            nick = domain.location.value,
            phone = domain.phone.value,
            spot = domain.spot.value,
            status = domain.status.value,
            wire = domain.wire.value,
            workspaceId = domain.workspaceId.value
        )
    }

}