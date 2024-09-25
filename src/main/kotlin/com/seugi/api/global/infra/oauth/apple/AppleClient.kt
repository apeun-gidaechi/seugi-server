package com.seugi.api.global.infra.oauth.apple

import com.seugi.api.global.auth.oauth.apple.AppleJWKSet
import com.seugi.api.global.infra.oauth.apple.res.AppleExchangeResponse
import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "appleClient", url = "\${oauth.apple.base-url}")
interface AppleClient {

    @PostMapping("/auth/token")
    fun exchange(@SpringQueryMap req: Map<String, String>): AppleExchangeResponse

    @GetMapping("/auth/keys")
    fun getPublicKeys(): AppleJWKSet

}