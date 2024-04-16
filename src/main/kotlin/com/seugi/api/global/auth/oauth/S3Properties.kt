package com.seugi.api.global.auth.oauth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class S3Properties (
    @Value("\${aws.s3.accessKey}") val accessKey: String,
    @Value("\${aws.s3.secretKey}") val secretKey: String,
    @Value("\${aws.s3.bucket}") val bucket: String
)