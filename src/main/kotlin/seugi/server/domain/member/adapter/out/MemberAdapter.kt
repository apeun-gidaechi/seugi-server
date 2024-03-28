package seugi.server.domain.member.adapter.out

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.mapper.MemberMapper
import seugi.server.domain.member.adapter.out.repository.MemberRepository
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.exception.MemberErrorCode
import seugi.server.domain.member.port.out.ExistMemberPort
import seugi.server.domain.member.port.out.LoadMemberPort
import seugi.server.domain.member.port.out.SaveMemberPort
import seugi.server.global.exception.CustomException

@Component
class MemberAdapter (
    val memberRepository: MemberRepository,
    val memberMapper: MemberMapper
) : SaveMemberPort, LoadMemberPort, ExistMemberPort {

    override fun saveMember(member: Member) {
        memberRepository.save(
            memberMapper.toEntity(member)
        )
    }

    override fun loadMemberWithId(id: Long): Member {
        return memberMapper.toDomain(
            memberRepository.findById(id)
                .orElseThrow {
                    CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                }
        )
    }

    override fun loadMemberWithEmail(email: String): Member {
        return memberMapper.toDomain(
            memberRepository.findByEmail(email)
                .orElseThrow {
                    CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                }
        )
    }

    override fun existMemberWithEmail(email: String): Boolean {
        return memberRepository.existsByEmail(email)
    }
}