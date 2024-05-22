package com.seugi.api.domain.email.adapter.out.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("code")
class CodeEntity (

    @Id
    val code: String,
    val token: String

)