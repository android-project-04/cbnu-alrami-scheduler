package cbnu.io.cbnualramischeduler.common.helper.fixture

import cbnu.io.cbnualramischeduler.business.core.domain.community_count.entity.CommunityCount
import cbnu.io.cbnualramischeduler.business.core.domain.community_count.infrastructure.CommunityCountJpaRepository
import cbnu.io.cbnualramischeduler.business.web.util.COMMUNITY_COUNT_KEY
import org.springframework.stereotype.Component

@Component
class CommunityCountFixture(
    private val communityCountJpaRepository: CommunityCountJpaRepository,
    private val redisFixture: RedisFixture,
) {
    var communityId = 1L
    fun createCommunityCount(): CommunityCount {
        redisFixture.incrementByKey("$COMMUNITY_COUNT_KEY$communityId")
        val communityCount = CommunityCount(
            communityId = communityId
        )
        this.communityId++
        return communityCountJpaRepository.save(communityCount)
    }
}
