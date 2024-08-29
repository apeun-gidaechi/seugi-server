package com.seugi.api.global.auth.jwt.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.seugi.api.global.exception.CustomErrorCode
import com.seugi.api.global.response.BaseResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class ErrorResponseSender (
    private val objectMapper: ObjectMapper
) {

    fun send(response: HttpServletResponse, code: CustomErrorCode) {
        try {
            response.status = code.status.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"
            response.writer.write(objectMapper.writeValueAsString(BaseResponse<Unit>(code)))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}