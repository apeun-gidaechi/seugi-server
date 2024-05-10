package com.seugi.api.domain.member.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.member.adapter.out.entity.QProfileEntity
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class ProfileRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : ProfileRepositoryCustom {

    override fun findByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): Optional<ProfileEntity> {
        val memberProfile = QProfileEntity.profileEntity

        val result = jpaQueryFactory
            .select(memberProfile)
            .from(memberProfile)
            .where(memberProfile.memberId.eq(memberId)
                .and(memberProfile.workspaceId.eq(workspaceId)))
            .fetchOne()

        return Optional.ofNullable(result)
    }

}