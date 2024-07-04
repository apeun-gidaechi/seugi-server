package com.seugi.api.domain.notice.presentation.controller

import com.seugi.api.domain.notice.service.NoticeService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class NoticeController(
    private val noticeService: NoticeService
)