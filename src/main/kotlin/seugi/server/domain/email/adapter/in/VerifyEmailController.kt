package seugi.server.domain.email.adapter.`in`

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.email.port.`in`.RetrieveTokenUseCase
import seugi.server.global.response.BaseResponse

@RestController
@RequestMapping("/email")
class VerifyEmailController (
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) {

    @GetMapping("/verify")
    fun getToken(
        @RequestParam code: String
    ): BaseResponse<String> {
        return retrieveTokenUseCase.retrieveToken(code)
    }

}