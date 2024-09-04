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

    override fun findByMemberIdAndProvider(id: MemberEntity, provider: String): OAuthEntity? {
        val entity = QOAuthEntity.oAuthEntity

        val result = jpaQueryFactory
            .select(entity)
            .from(entity)
            .where(
                entity.memberId.eq(id),
                entity.provider.eq(provider)
            ).fetchOne()

        return result
    }

    override fun existsByMemberIdAndProvider(id: MemberEntity, provider: String): Boolean {
        val entity: QOAuthEntity = QOAuthEntity.oAuthEntity

        return jpaQueryFactory
            .selectOne()
            .from(entity)
            .where(
                entity.memberId.eq(id),
                entity.provider.eq(provider)
            ).fetchOne() != null
    }

}