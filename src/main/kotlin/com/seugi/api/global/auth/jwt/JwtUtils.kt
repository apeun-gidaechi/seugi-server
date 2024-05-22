package com.seugi.api.global.auth.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.model.value.MemberRefreshToken
import com.seugi.api.domain.member.port.out.LoadMemberPort
import com.seugi.api.domain.member.port.out.SaveMemberPort
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils (
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService,
    private val loadMemberPort: LoadMemberPort
) {

    private val secretKey: SecretKey = SecretKeySpec(this.jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)

    fun getUsername(token: String): String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
            "email",
            String::class.java
        )
    }

    fun isExpired(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
            return false
        } catch (e: ExpiredJwtException) {
            return true
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

        return accessToken
    }

}