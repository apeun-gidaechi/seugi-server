package seugi.server.domain.member.adapter.out

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.mapper.MemberMapper
import seugi.server.domain.member.adapter.out.repository.MemberRepository
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.port.out.SaveMemberPort

@Component
class MemberAdapter (
    val memberRepository: MemberRepository,
    val memberMapper: MemberMapper
) : SaveMemberPort {

    override fun saveMember(member: Member) {
        memberRepository.save(
            memberMapper.returnMemberToMemberEntityWithoutId(member)
        )
    }
}