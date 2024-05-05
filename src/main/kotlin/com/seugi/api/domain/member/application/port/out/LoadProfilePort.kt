package com.seugi.api.domain.member.application.port.out.profile

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.ProfileEntity

interface LoadProfilePort {

    fun loadProfileWithMemberIdAndWorkspaceId(memberId: Long, workspaceId: String): ProfileEntity

}