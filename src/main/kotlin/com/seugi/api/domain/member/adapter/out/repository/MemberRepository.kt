package com.seugi.api.domain.member.adapter.out.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity

@Repository
interface MemberRepository: CrudRepository<MemberEntity, Long>, MemberRepositoryCustom {

    override fun findByEmail(email: String): MemberEntity?

    override fun existsByEmail(email: String): Boolean

}