package seugi.server.domain.member.adapter.out.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import seugi.server.domain.member.adapter.out.entity.MemberEntity

@Repository
interface MemberRepository: JpaRepository<MemberEntity, Long> {

    fun findByEmail(email: String): MemberEntity

    fun existsByEmail(email: String): Boolean

}