package com.seugi.api.global.response

interface ResponseInterface {
    val status: Int
    val success: Boolean
    val state: String
    val message: String
}