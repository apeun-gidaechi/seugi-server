package com.seugi.api.domain.member.adapter.out.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("refresh")
class MemberTokenEntity (

    @Id
    val accessToken: String,
    val refreshToken: String

)
