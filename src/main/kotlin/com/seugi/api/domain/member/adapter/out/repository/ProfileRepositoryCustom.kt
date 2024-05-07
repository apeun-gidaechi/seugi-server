package com.seugi.api.domain.member.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.ProfileEntity
import java.util.Optional

interface ProfileRepositoryCustom {

    fun findByMemberIdAndWorkspaceId(memberId: MemberEntity, workspaceId: String): Optional<ProfileEntity>

}