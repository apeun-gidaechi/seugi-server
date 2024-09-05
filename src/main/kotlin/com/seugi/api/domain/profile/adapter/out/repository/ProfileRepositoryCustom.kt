package com.seugi.api.domain.profile.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity

interface ProfileRepositoryCustom {

    fun findByMemberAndWorkspaceId(member: MemberEntity, workspaceId: String): ProfileEntity?
    fun existsByMemberAndWorkspaceId(member: MemberEntity, workspaceId: String): Boolean

}