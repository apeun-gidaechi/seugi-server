package seugi.server.domain.member.adapter.out

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.mapper.MemberMapper
import seugi.server.domain.member.adapter.out.repository.MemberRepository
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.port.out.ExistMemberPort
import seugi.server.domain.member.port.out.LoadMemberPort
import seugi.server.domain.member.port.out.SaveMemberPort

@Component
class MemberAdapter (
    val memberRepository: MemberRepository,
    val memberMapper: MemberMapper
) : SaveMemberPort, LoadMemberPort, ExistMemberPort {

    override fun saveMember(member: Member) {
        memberRepository.save(
            memberMapper.returnMemberToMemberEntityWithoutId(member)
        )
    }

    override fun loadMemberWithId(id: Long): Member {
        return memberMapper.returnMemberEntityToMember(
            memberRepository.findById(id).get()
        )
    }

    override fun loadMemberWithEmail(email: String): Member {
        return memberMapper.returnMemberEntityToMember(
            memberRepository.findByEmail(email)
        )
    }

    override fun existMemberWithEmail(email: String): Boolean {
        return memberRepository.existsByEmail(email)
    }
}