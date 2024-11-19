package com.seugi.api.global.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    override val status: Int = 1000,
    override val success: Boolean = false,
    override val state: String = "Error",
    override val message: String,
) : ResponseInterface
