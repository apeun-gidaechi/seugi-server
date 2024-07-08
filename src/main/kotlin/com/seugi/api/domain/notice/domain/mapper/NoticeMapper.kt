package com.seugi.api.domain.notice.domain.mapper

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.notice.domain.NoticeEntity
import com.seugi.api.domain.notice.domain.model.Notice
import com.seugi.api.domain.notice.presentation.dto.request.CreateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.response.NoticeResponse
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class NoticeMapper : Mapper<Notice, NoticeEntity> {
    override fun toDomain(entity: NoticeEntity): Notice {
        return Notice(
            id = entity.id!!,
            workspaceId = entity.workspaceId,
            user = entity.user!!,
            title = entity.title,
            content = entity.content,
            emoji = entity.emoji,
            creationDate = entity.creationDate.toString(),
            lastModifiedDate = entity.creationDate.toString(),
            deleted = entity.deleted
        )
    }

    override fun toEntity(domain: Notice): NoticeEntity {
        return NoticeEntity(
            workspaceId = domain.workspaceId,
            user = domain.user,
            title = domain.title,
            content = domain.content
        )
    }

    fun transferNoticeEntity(createNoticeRequest: CreateNoticeRequest, userEntity: MemberEntity): NoticeEntity {
        return toEntity(
            Notice(
                workspaceId = createNoticeRequest.workspaceId,
                user = userEntity,
                title = createNoticeRequest.title,
                content = createNoticeRequest.content
            )
        )
    }

    fun transferNoticeResponse(notice: Notice): NoticeResponse {
        return NoticeResponse(
            id = notice.id,
            workspaceId = notice.workspaceId,
            userName = notice.user.name,
            title = notice.title,
            content = notice.content,
            emoji = notice.emoji,
            creationDate = notice.creationDate.toString(),
            lastModifiedDate = notice.lastModifiedDate.toString()
        )
    }


}