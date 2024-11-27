package com.seugi.api.global.auth.jwt

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.exception.CustomException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.IllegalArgumentException

@Component
class JwtUtils( private val properties: JwtProperties ) {

    private val secretKey: SecretKey = SecretKeySpec(
        this.properties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )

    private fun create(member: Member, expiration: Long): String {
        val now: Long = Date().time

        return Jwts.builder()
            .id(member.id.toString())
            .issuedAt(Date(now))
            .expiration(Date(now + expiration))
            .signWith(secretKey)
            .compact()
    }

    fun generate(member: Member): JwtInfo {
        return JwtInfo(
            /* accessToken */"Bearer " + this.create(member, properties.accessExpire),
            /* refreshToken */ "Bearer " + this.create(member, properties.refreshExpire)
        )
    }

    fun refresh(member: Member): String {
        return "Bearer " + create(member, properties.accessExpire)
    }

    fun parse(token: String): Claims {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
        } catch (e: Exception) {
            val errorCode = when (e) {
                is ExpiredJwtException -> JwtErrorCode.EXPIRED
                is SignatureException -> JwtErrorCode.SIGNATURE_NOT_MATCH
                is MalformedJwtException -> JwtErrorCode.MALFORMED_STRUCT
                is UnsupportedJwtException -> JwtErrorCode.UNSUPPORTED_TYPE
                is IllegalArgumentException -> JwtErrorCode.ILLEGAL_ARGUMENT
                else -> JwtErrorCode.UNKNOWN
            }
            throw CustomException(errorCode)
        }
    }

}