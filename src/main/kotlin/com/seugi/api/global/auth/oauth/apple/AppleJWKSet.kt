package com.seugi.api.global.auth.oauth.apple

import com.fasterxml.jackson.annotation.JsonProperty

data class AppleJWKSet(
    @JsonProperty("keys") val keys: List<Keys>
) {

    data class Keys(
        @JsonProperty("kty") val kty: String,
        @JsonProperty("kid") val kid: String,
        @JsonProperty("use") val use: String,
        @JsonProperty("alg") val alg: String,
        @JsonProperty("n") val n: String,
        @JsonProperty("e") val e: String,
    )

    fun getMatchingKey(alg: String?, kid: String?): Keys  {
        return keys.first {
            key: Keys -> key.alg == alg && key.kid == kid
        }
    }

}