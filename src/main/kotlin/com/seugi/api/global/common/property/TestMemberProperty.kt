package com.seugi.api.domain.member.application.property

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TestMemberProperty (
    @Value("\${test.email}") val email: String,
    @Value("\${test.password}") val password: String,
    )