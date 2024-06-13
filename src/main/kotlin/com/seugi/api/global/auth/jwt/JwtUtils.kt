package com.seugi.api.global.auth.jwt

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.global.auth.jwt.exception.type.JwtErrorType
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils (
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService,
) {

    private val secretKey: SecretKey = SecretKeySpec(this.jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)

    fun getUsername(token: String): String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
            "email",
            String::class.java
        )
    }

    fun isExpired(token: String): JwtErrorType {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
            return JwtErrorType.OK
        } catch (e: ExpiredJwtException) {
            return JwtErrorType.ExpiredJwtException
        } catch (e: SignatureException) {
            return JwtErrorType.SignatureException
        } catch (e: MalformedJwtException) {
            return JwtErrorType.MalformedJwtException
        }
    }

    fun getToken(token: String): String {
        return token.substring(7)
    }

    fun generate(member: Member): JwtInfo {
        val now: Long = Date().time

        val accessToken = Jwts.builder()
            .claim("id", member.id?.value)
            .claim("email", member.email.value)
            .claim("role", member.role.value)
            .issuedAt(Date(now))
            .expiration(Date(now + jwtProperties.accessExpired))
            .signWith(secretKey)
            .compact()

        val refreshToken = Jwts.builder()
            .claim("id", member.id?.value)
            .claim("email", member.email.value)
            .claim("role", member.role.value)
            .issuedAt(Date(now))
            .expiration(Date(now + jwtProperties.refreshExpired))
            .signWith(secretKey)
            .compact()

        return JwtInfo("Bearer $accessToken", "Bearer $refreshToken")
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsername(token))

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun refreshToken(member: Member): String {
        val now: Long = Date().time

        val accessToken = Jwts.builder()
            .claim("id", member.id?.value)
            .claim("email", member.email.value)
            .claim("role", member.role.value)
            .issuedAt(Date(now))
            .expiration(Date(now + jwtProperties.refreshExpired))
            .signWith(secretKey)
            .compact()

        return "Bearer $accessToken"
    }

}