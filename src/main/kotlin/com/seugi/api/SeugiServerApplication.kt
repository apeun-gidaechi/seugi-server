package com.seugi.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@ConfigurationPropertiesScan
class SeugiServerApplication

fun main(args: Array<String>) {
    runApplication<SeugiServerApplication>(*args)
}
