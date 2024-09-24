package com.seugi.api.domain.file.service

import com.seugi.api.domain.file.exception.FileErrorCode
import com.seugi.api.domain.file.presentation.dto.response.FileResponse
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.infra.aws.s3.service.S3
import com.seugi.api.global.infra.aws.s3.type.FileType
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileServiceImpl(
    private val s3: S3
): FileService {

    override fun uploadFile(type: FileType, file: MultipartFile): BaseResponse<FileResponse> {
        if (file.isEmpty) throw CustomException(FileErrorCode.MEDIA_TYPE_ERROR)

        return BaseResponse(
            message = "파일 업로드 성공, 업로드 타입 = $type",
            data =
            s3.uploadMultipleFile(
                file = file,
                type = type
            )
        )

    }
}