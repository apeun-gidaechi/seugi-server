package seugi.server.domain.member.adapter.out.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import java.util.Optional

@Repository
interface MemberRepository: JpaRepository<MemberEntity, Long> {

    fun findByEmail(email: String): Optional<MemberEntity>

    fun existsByEmail(email: String): Boolean

}