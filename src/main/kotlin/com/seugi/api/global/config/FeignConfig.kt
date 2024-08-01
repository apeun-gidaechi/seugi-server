package com.seugi.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import feign.Util
import feign.codec.Decoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.BufferedReader
import java.io.InputStreamReader


@Configuration
class FeignConfig {

    @Bean
    fun feignDecoder(): Decoder {
        return Decoder { response, type ->
            val bodyStr = Util.toString(
                BufferedReader(
                    InputStreamReader(
                        response.body().asInputStream(),
                        Charsets.UTF_8
                    )
                )
            )
            val javaType = TypeFactory.defaultInstance().constructType(type)
            ObjectMapper().readValue(bodyStr, javaType)
        }
    }

}