package com.seugi.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SeugiServerApplication

fun main(args: Array<String>) {
    runApplication<SeugiServerApplication>(*args)
}
