package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.application.port.`in`.RetrieveProfileUseCase
import org.springframework.web.bind.annotation.RestController

@RestController
class RetrieveMemberController (
    private val retrieveMemberUseCase: RetrieveProfileUseCase
)
{

}