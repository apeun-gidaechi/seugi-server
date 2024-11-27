package com.seugi.api.domain.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

    override fun findByEmail(email: String): MemberEntity?
    override fun existsByEmail(email: String): Boolean

}