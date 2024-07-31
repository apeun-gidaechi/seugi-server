package com.seugi.api.global.infra.discord.webhook

import org.joda.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.io.PrintWriter
import java.io.StringWriter

@Service
class DiscordErrorSendService(
    private val discordClient: DiscordClient,
) {
    fun sendDiscordAlarm(e: Exception, request: WebRequest) {
        discordClient.sendAlarm(createMessage(e, request))
    }

    fun createMessage(e: Exception, request: WebRequest): DiscordMessage {
        return DiscordMessage(
            content = "# \uD83D\uDEA8 에러 발생",
            embeds = listOf(
                Embed(
                    title = "\uD83D\uDEA8️ 에러 정보",
                    description = "### 🕖 발생 시간\n"
                            + LocalDateTime.now()
                            + "\n"
                            + "### 🔗 요청 URL\n"
                            + createRequestFullPath(request)
                            + "\n"
                            + "### 📄 Stack Trace\n"
                            + "```\n"
                            + getStackTrace(e).substring(0, 200)
                            + "\n```"
                )
            )
        )

    }

    fun createRequestFullPath(webRequest: WebRequest): String {
        val request = (webRequest as ServletWebRequest).request
        var fullPath = "${request.method} ${request.requestURL}"

        val queryString = request.queryString
        if (!queryString.isNullOrEmpty()) {
            fullPath += "?$queryString"
        }

        return fullPath
    }

    fun getStackTrace(e: Exception): String = StringWriter().apply {
        e.printStackTrace(PrintWriter(this))
    }.toString()
}