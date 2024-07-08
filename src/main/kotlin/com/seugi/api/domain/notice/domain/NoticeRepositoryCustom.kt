package com.seugi.api.domain.notice.domain

interface NoticeRepositoryCustom {
    fun findByWorkspaceId(workspaceId: String): List<NoticeEntity>
}