package com.seugi.api.domain.file.service

import com.seugi.api.global.infra.aws.s3.type.FileType
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun uploadFile(type: FileType, file: MultipartFile): BaseResponse<String>
}