package com.seugi.api.domain.member.application.model.value

data class MemberFCMToken(
    val token: MutableSet<String> = mutableSetOf(),
)