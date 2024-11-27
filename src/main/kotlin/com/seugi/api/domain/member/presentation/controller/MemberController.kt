package com.seugi.api.domain.member.presentation.controller

import com.seugi.api.domain.member.presentation.controller.dto.req.EditMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.LoginMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.LogoutMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.member.service.MemberService
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberController ( private val service: MemberService ) {

    @PostMapping("/register")
    fun register(@RequestBody dto: RegisterMemberRequest): BaseResponse<JwtInfo> {
        return BaseResponse( data = service.register(dto), message = "회원가입을 완료했습니다" )
    }

    @PostMapping("/login")
    fun login(@RequestBody dto: LoginMemberRequest): BaseResponse<JwtInfo> {
        return BaseResponse( data = service.login(dto), message = "로그인을 완료했습니다" )
    }

    @GetMapping("/myInfo")
    fun retrieve(@GetResolvedMember model: Member): BaseResponse<RetrieveMemberResponse> {
        return BaseResponse( data = service.retrieve(model), message = "내 정보 조회를 완료했습니다" )
    }

    @GetMapping("/refresh")
    fun refresh(token: String): BaseResponse<String> {
        return BaseResponse( data = service.refresh(token), message = "액세스 토큰 재발급을 완료했습니다" )
    }

    @PatchMapping("/edit")
    fun edit(@RequestBody dto: EditMemberRequest, @GetResolvedMember model: Member) : BaseResponse<Unit> {
        return BaseResponse( data = service.edit(dto, model), message = "내 정보 수정을 완료했습니다" )
    }

    @PostMapping("/logout")
    fun logout(@RequestBody logoutMemberRequest: LogoutMemberRequest, @GetResolvedMember model: Member): BaseResponse<Unit> {
        return BaseResponse( data = service.logout(logoutMemberRequest.fcmToken, model), message = "로그아웃을 완료했습니다" )
    }

    @DeleteMapping("/remove")
    fun removeMember(@GetResolvedMember model: Member): BaseResponse<Unit> {
        return BaseResponse( data = service.delete(model), message = "회원 탈퇴를 완료했습니다" )
    }

}