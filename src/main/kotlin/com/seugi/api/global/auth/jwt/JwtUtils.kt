package com.seugi.api.global.auth.jwt

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.exception.CustomException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
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
class JwtUtils(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService
) {

    private val secretKey: SecretKey = SecretKeySpec(
        this.jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )

    fun getUsername(token: String): String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
            "email",
            String::class.java
        )
    }

    fun checkTokenInfo(token: String) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_EXPIRED)
        } catch (e: SignatureException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_SIGNATURE_ERROR)
        } catch (e: MalformedJwtException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_ERROR)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_UNSUPPORTED_ERROR)
        } catch (e: IllegalArgumentException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_ILL_EXCEPTION)
        } catch (e: Exception) {
            throw CustomException(JwtErrorCode.JWT_UNKNOWN_EXCEPTION)
        }
    }

    fun getToken(token: String): String {
        return token.removePrefix("Bearer ")
    }

    fun generate(member: Member): JwtInfo {
        val accessToken = createToken(
            member = member,
            tokenExpired = jwtProperties.accessExpired
        )
        val refreshToken = createToken(
            member = member,
            tokenExpired = jwtProperties.refreshExpired
        )


        return JwtInfo("Bearer $accessToken", "Bearer $refreshToken")
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsername(getToken(token)))
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun refreshToken(member: Member): String {

        return "Bearer " + createToken(
            member = member,
            tokenExpired = jwtProperties.accessExpired
        )
    }

    private fun createToken(member: Member, tokenExpired: Long): String {
        val now: Long = Date().time
        return Jwts.builder()
            .claim("id", member.id?.value)
            .claim("email", member.email.value)
            .claim("role", member.role.value)
            .issuedAt(Date(now))
            .expiration(Date(now + tokenExpired))
            .signWith(secretKey)
            .compact()
    }

}