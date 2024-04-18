package com.seugi.api.domain.file.presentation

import com.seugi.api.domain.file.service.FileService
import com.seugi.api.global.infra.aws.s3.type.FileType
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController("/file")
class FileController(
    private val fileService: FileService
) {

    @PostMapping("/file/{type}")
    fun uploadFile(
        @PathVariable type: FileType,
        @RequestParam("file") file: MultipartFile
    ): BaseResponse<String> {
        return fileService.uploadFile(type, file);
    }

}