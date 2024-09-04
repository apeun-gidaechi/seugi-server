package com.seugi.api.global.infra.oauth.google.parse

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleParseClient", url = "https://oauth2.googleapis.com/tokeninfo")
interface GoogleParseClient {

    @GetMapping
    fun parse(@RequestParam("id_token") token: String): GoogleParseResponse

}