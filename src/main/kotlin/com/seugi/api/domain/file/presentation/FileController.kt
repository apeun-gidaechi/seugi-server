package com.seugi.api.domain.file.presentation

import com.seugi.api.domain.file.service.FileService
import com.seugi.api.global.infra.aws.s3.type.FileType
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/file")
class FileController(
    private val fileService: FileService
) {

    @PostMapping("/upload/{type}")
    fun uploadFile(
        @PathVariable type: FileType,
        @RequestPart("file") file: MultipartFile
    ): BaseResponse<String> {
        return fileService.uploadFile(type, file)
    }

}