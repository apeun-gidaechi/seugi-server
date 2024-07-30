package com.seugi.api.global.infra.discord.webhook

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(name = "discord-client", url = "\${discord.webhook.url}")
interface DiscordClient {
    @PostMapping
    fun sendAlarm(
        @RequestBody message: Any,
    )
}