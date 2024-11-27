package com.seugi.api.domain.member.service

import com.seugi.api.domain.email.application.service.ConfirmCodeService
import com.seugi.api.domain.member.presentation.controller.dto.req.EditMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.LoginMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.exception.MemberErrorCode
import com.seugi.api.domain.member.domain.MemberRepository
import com.seugi.api.domain.member.domain.mapper.MemberMapper
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.exception.CustomException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl (
    private val bcrypt: BCryptPasswordEncoder,
    private val confirmCodeService: ConfirmCodeService,
    private val repository: MemberRepository,
    private val mapper: MemberMapper,
    private val utils: JwtUtils
) : MemberService  {

    @Transactional
    override fun save(model: Member): Member {
        val entity = repository.save(mapper.toEntity(model))

        return mapper.toDomain(entity)
    }

    @Transactional
    override fun findById(id: Long): Member {
        val entity = repository.findByIdOrNull(id)
            ?: throw CustomException(MemberErrorCode.NOT_FOUND)

        return mapper.toDomain(entity)
    }

    @Transactional
    override fun findByIdOrNull(id: Long): Member? {
        val entity = repository.findByIdOrNull(id)

        return entity?.let { mapper.toDomain(it) }
    }

    @Transactional
    override fun findByEmail(email: String): Member {
        val entity = repository.findByEmail(email)
            ?: throw CustomException(MemberErrorCode.NOT_FOUND)

        return mapper.toDomain(entity)
    }

    @Transactional
    override fun findByEmailOrNull(email: String): Member? {
        val entity = repository.findByEmail(email)

        return entity?.let { mapper.toDomain(it) }
    }

    @Transactional
    override fun register(dto: RegisterMemberRequest): JwtInfo {
        val model = Member(dto, bcrypt.encode(dto.password))

        /* todo: refactor */ confirmCodeService.confirmCode(dto.email, dto.code)

        if (repository.existsByEmail(model.email)) {
            throw CustomException(MemberErrorCode.ALREADY_EXIST)
        }

        val entity = this.save(model)

        return utils.generate(entity)
    }

    @Transactional
    override fun login(dto: LoginMemberRequest): JwtInfo {
        val model = this.findByEmail(dto.email)

        if (!bcrypt.matches(dto.password, model.password)) {
            throw CustomException(MemberErrorCode.PASSWORD_NOT_MATCH)
        }

        model.fcmToken.add(dto.token)

        this.save(model)

        return utils.generate(model)
    }

    @Transactional
    override fun retrieve(model: Member): RetrieveMemberResponse {
        return RetrieveMemberResponse(model)
    }

    @Transactional
    override fun refresh(token: String): String {
        val email = utils.parse(token.removePrefix("Bearer "))["email"] as String

        val model = this.findByEmail(email)

        return utils.refresh(model)
    }

    @Transactional
    override fun edit(dto: EditMemberRequest, model: Member) {
        model.picture = dto.picture
        model.name = dto.name
        model.birth = dto.birth

        this.save(model)
    }

    @Transactional
    override fun logout(fcmToken: String, model: Member) {
        model.fcmToken.remove(fcmToken)

        this.save(model)
    }

    /*
        TODO : 멤버랑 관련된 레코드 soft-delete 처리
    */
    @Transactional
    override fun delete(model: Member) {
        model.deleted = true
        model.fcmToken.clear()

        this.save(model)
    }

}