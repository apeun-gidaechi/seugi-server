package com.seugi.api.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FCMConfig(
    @Value("\${fcm.file.name}")
    private val path: String,
) {
    @PostConstruct
    fun initialize() {
        val options = FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(ClassPathResource(path).inputStream)
            )
            .build()
        FirebaseApp.initializeApp(options)
    }
}