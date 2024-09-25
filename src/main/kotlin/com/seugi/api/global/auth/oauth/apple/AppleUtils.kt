package com.seugi.api.global.auth.oauth.apple

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.seugi.api.global.infra.oauth.apple.AppleClient
import com.seugi.api.global.infra.oauth.apple.req.AppleExchangeRequest
import com.seugi.api.global.infra.oauth.apple.res.AppleExchangeResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.*
import java.security.spec.RSAPublicKeySpec
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class AppleUtils (
    private val properties: AppleProperties,
    private val client: AppleClient,
    private val mapper: ObjectMapper
) {

    fun exchange(code: String, clientId: String): AppleExchangeResponse {
        val request = AppleExchangeRequest (
            code,
            clientId,
            generateClientSecret(clientId),
            properties
        )

        return client.exchange(request.toQueryMap())
    }

    fun getPublicKeys(): AppleJWKSet {
        return client.getPublicKeys()
    }

    fun generateClientSecret(clientId: String): String {
        val expiration = LocalDateTime.now().plusMinutes(5)

        return Jwts.builder()
            .header()
            .keyId(properties.keyId)
            .and()
            .issuer(properties.teamId)
            .audience()
            .add(properties.baseUrl)
            .and()
            .subject(clientId)
            .expiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
            .issuedAt(Date())
            .signWith(getPrivateKey(), Jwts.SIG.ES256)
            .compact()
    }

    fun getPrivateKey(): PrivateKey {
        Security.addProvider(BouncyCastleProvider())

        val converter = JcaPEMKeyConverter().setProvider("BC")
        val privateKeyBytes = Base64.getDecoder().decode(properties.privateKey)
        val privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes)

        return converter.getPrivateKey(privateKeyInfo)
    }

    fun parseHeader(idToken: String): Map<String, String> {
        val encodedHeader: String = idToken.split("\\.".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()[0]

        val decodedHeader = String(Base64.getUrlDecoder().decode(encodedHeader))

        return mapper.readValue(decodedHeader, object : TypeReference<Map<String, String>>() {})
    }

    fun generate(headers: Map<String, String>, keys: AppleJWKSet): PublicKey {
        val applePublicKey = keys.getMatchingKey(
            headers["alg"],
            headers["kid"]
        )

        return generatePublicKey(applePublicKey)
    }

    fun generatePublicKey(applePublicKey: AppleJWKSet.Keys): PublicKey {
        val nBytes: ByteArray = Base64.getUrlDecoder().decode(applePublicKey.n)
        val eBytes: ByteArray = Base64.getUrlDecoder().decode(applePublicKey.e)

        val n = BigInteger(1, nBytes)
        val e = BigInteger(1, eBytes)
        val rsaPublicKeySpec = RSAPublicKeySpec(n, e)

        val keyFactory = KeyFactory.getInstance(applePublicKey.kty)

        return keyFactory.generatePublic(rsaPublicKeySpec)
    }

    fun extractClaims(idToken: String, publicKey: PublicKey): Claims {
        return Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(idToken)
            .payload
    }

}