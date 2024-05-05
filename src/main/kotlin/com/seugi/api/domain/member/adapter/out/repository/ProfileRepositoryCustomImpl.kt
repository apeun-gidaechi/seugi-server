package com.seugi.api.domain.member.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.MemberProfileEntity
import com.seugi.api.domain.member.adapter.out.entity.QMemberProfileEntity
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.global.exception.CustomException
import org.springframework.stereotype.Repository

@Repository
class MemberProfileRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : MemberProfileRepositoryCustom {

    override fun findByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): MemberProfileEntity {
        val memberProfile = QMemberProfileEntity.memberProfileEntity

        return jpaQueryFactory
            .select(memberProfile)
            .from(memberProfile)
            .where(memberProfile.memberId.eq(memberId)
                .and(memberProfile.workspaceId.eq(workspaceId)))
            .fetchOne() ?: throw CustomException(MemberErrorCode.PROFILE_NOT_FOUND)
    }

}