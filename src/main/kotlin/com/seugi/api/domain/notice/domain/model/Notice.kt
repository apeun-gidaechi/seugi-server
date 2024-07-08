package com.seugi.api.domain.notice.domain.model

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity

data class Notice(
    val id: Long? = null,
    val workspaceId: String,
    val user: MemberEntity,
    val title: String,
    val content: String,
    val emoji: List<String>? = null,
    val creationDate: String? = null,
    val lastModifiedDate: String? = null,
    val deleted: Boolean = false,
)