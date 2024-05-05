package com.seugi.api.domain.member.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.MemberProfileEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberProfileRepository : CrudRepository<MemberProfileEntity, Long>, MemberProfileRepositoryCustom {

    override fun findByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): MemberProfileEntity

}