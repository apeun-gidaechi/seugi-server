package com.seugi.api.global.config

import com.google.api.client.http.javanet.NetHttpTransport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class SpringConfig {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun netHttpTransport(): NetHttpTransport {
        return NetHttpTransport()
    }

}