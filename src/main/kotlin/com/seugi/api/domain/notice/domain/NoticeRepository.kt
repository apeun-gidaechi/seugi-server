package com.seugi.api.domain.notice.domain

import org.springframework.data.repository.CrudRepository

interface NoticeRepository : CrudRepository<NoticeEntity, Long>, NoticeRepositoryCustom {
    override fun findByWorkspaceId(workspaceId: String): List<NoticeEntity>
}