package com.seugi.api.domain.oauth.adapter.out.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity
import com.seugi.api.domain.oauth.adapter.out.entity.QOAuthEntity
import org.springframework.stereotype.Repository

@Repository
class OAuthRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : OAuthCustomRepository {

    override fun findByMemberAndProvider(member: MemberEntity, provider: String): OAuthEntity? {
        val entity = QOAuthEntity.oAuthEntity

        return jpaQueryFactory
            .select(entity)
            .from(entity)
            .where(
                entity.member.eq(member),
                entity.provider.eq(provider)
            )
            .fetchOne()
    }

    override fun existsByMemberAndProvider(member: MemberEntity, provider: String): Boolean {
        val entity = QOAuthEntity.oAuthEntity

        return jpaQueryFactory
            .selectOne()
            .from(entity)
            .where(
                entity.member.eq(member),
                entity.provider.eq(provider)
            ).fetchOne() != null
    }

}