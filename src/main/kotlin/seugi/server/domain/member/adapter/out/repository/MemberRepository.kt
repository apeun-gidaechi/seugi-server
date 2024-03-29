package seugi.server.domain.member.adapter.out.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import java.util.Optional

@Repository
interface MemberRepository: CrudRepository<MemberEntity, Long> {

    fun findByEmail(email: String): Optional<MemberEntity>

    fun existsByEmail(email: String): Boolean

}