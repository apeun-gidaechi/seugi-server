package com.seugi.api.global.infra.oauth.google.parse

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleParseClient", url = "\${oauth.google.parse-url}")
interface GoogleParseClient {

    @GetMapping
    fun parse(@RequestParam("id_token") token: String): GoogleParseResponse

}