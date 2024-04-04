package seugi.server.domain.email.adapter.out.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class EmailRepository (
    private val redisTemplate: StringRedisTemplate
) {

    fun saveToken(code: String, token: String) {
        val valueOps = redisTemplate.opsForValue()

        val expire = java.time.Duration.ofMinutes(10)

        valueOps.set(code, token, expire)
    }

    fun getToken(code: String): Optional<String> {
        val valueOps = redisTemplate.opsForValue()

        val token: String? = valueOps.getAndDelete(code)

        return Optional.ofNullable(token)
    }

    fun saveEmail(token: String, email: String) {
        val valueOps = redisTemplate.opsForValue()

        val expire = java.time.Duration.ofMinutes(10)

        valueOps.set(token, email, expire)
    }

    fun loadEmail(token: String): Optional<String> {
        val valueOps = redisTemplate.opsForValue()

        val email: String? = valueOps.getAndDelete(token)

        return Optional.ofNullable(email)
    }

    fun deleteToken(code: String) {
        val valueOps = redisTemplate.opsForValue()
    }

}