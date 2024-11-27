package com.seugi.api.domain.profile.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.member.domain.MemberEntity
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.profile.adapter.out.entity.QProfileEntity
import org.springframework.stereotype.Repository

@Repository
class ProfileRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : ProfileRepositoryCustom {

    override fun findByMemberAndWorkspaceId(member: MemberEntity, workspaceId: String): ProfileEntity? {
        val entity = QProfileEntity.profileEntity

        return jpaQueryFactory
            .select(entity)
            .from(entity)
            .where(
                entity.member.eq(member)
                .and(entity.workspaceId.eq(workspaceId))
            )
            .fetchOne()
    }

    override fun existsByMemberAndWorkspaceId(member: MemberEntity, workspaceId: String): Boolean {
        val entity = QProfileEntity.profileEntity

        return jpaQueryFactory
            .select(entity)
            .from(entity)
            .where(
                entity.member.eq(member)
                .and(entity.workspaceId.eq(workspaceId))
            )
            .fetchFirst() != null
    }

}