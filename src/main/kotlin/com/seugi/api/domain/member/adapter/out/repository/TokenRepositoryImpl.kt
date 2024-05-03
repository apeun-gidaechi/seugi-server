package com.seugi.api.domain.member.adapter.out.repository

import com.seugi.api.global.auth.jwt.JwtProperties
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.TimeUnit

@Repository
class TokenRepositoryImpl (
    private val redisTemplate: StringRedisTemplate,
    private val jwtProperties: JwtProperties
) : TokenRepository{

    override fun saveToken(accessToken: String, refreshToken: String) {
        redisTemplate.opsForValue().set(accessToken, refreshToken, jwtProperties.refreshExpired, TimeUnit.MILLISECONDS)
    }

    override fun loadToken(accessToken: String): Optional<String> {
        val token: String? = redisTemplate.opsForValue().getAndDelete(accessToken)

        return Optional.ofNullable(token)
    }

}