package com.seugi.api.domain.notice.domain.mapper

import com.seugi.api.domain.notice.domain.NoticeEntity
import com.seugi.api.domain.notice.domain.model.Notice
import com.seugi.api.global.common.Mapper

class NoticeMapper : Mapper<Notice, NoticeEntity> {
    override fun toDomain(entity: NoticeEntity): Notice {
        TODO("Not yet implemented")
    }

    override fun toEntity(domain: Notice): NoticeEntity {
        TODO("Not yet implemented")
    }
}