package cbnu.io.cbnualramischeduler.business.core.domain.community_count.infrastructure

import cbnu.io.cbnualramischeduler.business.core.domain.community_count.entity.CommunityCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityCountJpaRepository: JpaRepository<CommunityCount, Long> {

    fun findByCommunityId(id: Long): CommunityCount?
}
