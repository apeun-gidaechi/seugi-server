package com.seugi.api.domain.member.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.QMemberEntity

@Repository
class MemberRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun findByEmail(email: String): MemberEntity? {
        val entity = QMemberEntity.memberEntity

        return jpaQueryFactory
            .select(entity)
            .from(entity)
            .where(
                entity.email.eq(email),
                entity.deleted.eq(false)
            )
            .fetchOne()
    }

    override fun existsByEmail(email: String): Boolean {
        val entity = QMemberEntity.memberEntity

        return jpaQueryFactory
            .selectOne()
            .from(entity)
            .where(
                entity.email.eq(email),
                entity.deleted.eq(false)
            )
            .fetchOne() != null
    }

}