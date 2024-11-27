package com.seugi.api.global.config

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActuatorHttpExchangesConfiguration {
    @Bean
    fun httpTraceRepository(): HttpExchangeRepository {
        return InMemoryHttpExchangeRepository()
    }
}