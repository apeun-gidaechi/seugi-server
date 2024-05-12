package com.seugi.api.domain.member.adapter.out

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import com.seugi.api.domain.member.adapter.out.mapper.MemberMapper
import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.member.application.port.out.ExistMemberPort
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.exception.CustomException

@Component
class MemberAdapter (
    val memberRepository: MemberRepository,
    val memberMapper: MemberMapper
) : SaveMemberPort, LoadMemberPort, ExistMemberPort {

    @Transactional
    override fun saveMember(member: Member) {
        memberRepository.save(
            memberMapper.toEntity(member)
        )
    }

    @Transactional(readOnly = true)
    override fun loadMemberWithId(id: Long): Member {
        return memberMapper.toDomain(
            memberRepository.findById(id)
                .orElseThrow {
                    CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                }
        )
    }

    @Transactional(readOnly = true)
    override fun loadMemberWithEmail(email: String): Member {
        return memberMapper.toDomain(
            memberRepository.findByEmail(email)
                .orElseThrow {
                    CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                }
        )
    }

    @Transactional(readOnly = true)
    override fun existMemberWithEmail(email: String): Boolean {
        return memberRepository.existsByEmail(email)
    }
}