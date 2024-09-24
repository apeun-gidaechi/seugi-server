package com.seugi.api.global.infra.aws.s3.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "aws.s3")
class S3Properties @ConstructorBinding constructor (

    val accessKey: String,
    val secretKey: String,
    val bucket: String

)