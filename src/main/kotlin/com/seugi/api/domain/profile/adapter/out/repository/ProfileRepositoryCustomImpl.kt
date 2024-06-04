package com.seugi.api.domain.profile.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.profile.adapter.out.entity.QProfileEntity
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class ProfileRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : ProfileRepositoryCustom {

    override fun findByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): Optional<ProfileEntity> {
        val profileEntity = QProfileEntity.profileEntity

        val result = jpaQueryFactory
            .select(profileEntity)
            .from(profileEntity)
            .where(profileEntity.memberId.eq(memberId)
                .and(profileEntity.workspaceId.eq(workspaceId)))
            .fetchOne()

        return Optional.ofNullable(result)
    }

    override fun existByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): Boolean {
        val profileEntity = QProfileEntity.profileEntity

        return jpaQueryFactory
            .select(profileEntity)
            .from(profileEntity)
            .where(profileEntity.memberId.eq(memberId)
                .and(profileEntity.workspaceId.eq(workspaceId)))
            .fetchFirst() != null
    }

}