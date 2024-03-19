package seugi.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeugiServerApplication

fun main(args: Array<String>) {
    runApplication<SeugiServerApplication>(*args)
}
