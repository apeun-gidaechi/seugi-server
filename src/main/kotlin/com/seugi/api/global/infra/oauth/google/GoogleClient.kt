package com.seugi.api.global.infra.oauth.google

import com.seugi.api.global.infra.oauth.google.req.GoogleExchangeRequest
import com.seugi.api.global.infra.oauth.google.res.GoogleExchangeResponse
import com.seugi.api.global.infra.oauth.google.res.GoogleParseResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleClient", url = "\${oauth.google.base-url}")
interface GoogleClient {

    @PostMapping("/token")
    fun exchange(param: GoogleExchangeRequest): GoogleExchangeResponse

    @GetMapping("/tokeninfo")
    fun parse(@RequestParam("id_token") token: String): GoogleParseResponse

}