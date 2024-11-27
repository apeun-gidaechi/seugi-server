import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.member.domain.MemberEntity
import com.seugi.api.domain.member.domain.MemberRepositoryCustom
import com.seugi.api.domain.member.domain.QMemberEntity
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryCustomImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun findByEmail(email: String): MemberEntity? {
        val entity = QMemberEntity.memberEntity

        return jpaQueryFactory
            .select(entity)
            .from(entity)
            .where(
                entity.email.eq(email),
                entity.deleted.eq(false)
            )
            .fetchOne()
    }

    override fun existsByEmail(email: String): Boolean {
        val entity = QMemberEntity.memberEntity

        return jpaQueryFactory
            .selectOne()
            .from(entity)
            .where(
                entity.email.eq(email),
                entity.deleted.eq(false)
            )
            .fetchOne() != null
    }

}