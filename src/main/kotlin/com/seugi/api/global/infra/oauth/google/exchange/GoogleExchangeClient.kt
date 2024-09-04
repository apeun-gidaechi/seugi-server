package com.seugi.api.global.infra.oauth.google.exchange

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "googleExchangeClient", url = "https://oauth2.googleapis.com/token")
interface GoogleExchangeClient {

    @PostMapping
    fun exchange(param: GoogleExchangeRequest): GoogleExchangeResponse

}