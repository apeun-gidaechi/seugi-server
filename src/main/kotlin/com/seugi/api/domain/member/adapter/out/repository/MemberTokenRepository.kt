package com.seugi.api.domain.member.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberTokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberTokenRepository: CrudRepository<MemberTokenEntity, String> {

}