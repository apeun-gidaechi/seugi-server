package com.seugi.api.domain.email.adapter.out.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

// TTL = 10 분
@RedisHash("code", timeToLive = 600L)
class CodeEntity (

    @Id
    val email: String,
    val code: String

)