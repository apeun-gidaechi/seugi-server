package com.seugi.api.domain.member.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.QMemberEntity
import java.util.*

@Repository
class MemberRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun findByEmail(email: String): Optional<MemberEntity> {
        val memberEntity: QMemberEntity = QMemberEntity.memberEntity

        val result = jpaQueryFactory
            .select(memberEntity)
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne()

        return Optional.ofNullable(result)
    }

    override fun existsByEmail(email: String): Boolean {
        val memberEntity: QMemberEntity = QMemberEntity.memberEntity

        return jpaQueryFactory
            .selectOne()
            .from(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne() != null
    }

}