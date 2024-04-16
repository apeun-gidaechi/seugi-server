package com.seugi.api.global.infra

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.seugi.api.global.auth.oauth.S3Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Configuration
class S3(
    val s3Properties: S3Properties
) {
    @Bean
    fun amazonS3Client(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(
                AWSStaticCredentialsProvider(BasicAWSCredentials(s3Properties.accessKey, s3Properties.secretKey))
            )
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }

    fun uploadMultipleFile(
        @RequestPart file: MultipartFile,
        fileName: String
    ): String {
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = file.size
        }
        val putObjectRequest = PutObjectRequest(
            s3Properties.bucket,
            fileName,
            file.inputStream,
            objectMetadata,
        )

        amazonS3Client().putObject(putObjectRequest)

        val fileUrl = "https://s3." + Regions.AP_NORTHEAST_2.name + ".amazonaws.com/" + s3Properties.bucket + "/" + fileName

        return fileUrl
    }
}