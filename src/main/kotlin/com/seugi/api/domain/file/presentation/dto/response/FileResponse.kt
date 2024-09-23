package com.seugi.api.domain.file.presentation.dto.response

data class FileResponse(
    val url: String,
    val name: String,
    val byte: Long,
)
