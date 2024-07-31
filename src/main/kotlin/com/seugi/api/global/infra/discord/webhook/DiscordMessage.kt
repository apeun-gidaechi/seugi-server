package com.seugi.api.global.infra.discord.webhook

data class DiscordMessage(
    val content: String = "",
    val embeds: List<Embed> = emptyList(),
)

data class Embed(
    val title: String = "",
    val description: String = "",
)