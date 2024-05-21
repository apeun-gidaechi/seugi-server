package com.seugi.api.domain.profile.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProfileRepository : CrudRepository<ProfileEntity, Long>, ProfileRepositoryCustom {

    override fun findByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): Optional<ProfileEntity>

}