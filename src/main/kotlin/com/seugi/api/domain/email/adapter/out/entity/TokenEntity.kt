package com.seugi.api.domain.email.adapter.out.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("token")
class TokenEntity (

    @Id
    val token: String,
    val email: String

)