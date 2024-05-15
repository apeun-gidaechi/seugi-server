package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.global.response.BaseResponse

interface EditMemberUseCase {

    fun editPicture(url: String, id: Long): BaseResponse<Unit>

}