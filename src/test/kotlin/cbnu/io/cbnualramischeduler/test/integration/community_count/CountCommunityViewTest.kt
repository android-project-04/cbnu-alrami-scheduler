package cbnu.io.cbnualramischeduler.test.integration.community_count

import cbnu.io.cbnualramischeduler.business.core.domain.community_count.infrastructure.CommunityCountJpaRepository
import cbnu.io.cbnualramischeduler.business.web.community_count.CommunityCounter
import cbnu.io.cbnualramischeduler.business.web.util.COMMUNITY_COUNT_KEY
import cbnu.io.cbnualramischeduler.common.container.TestContainerBase
import cbnu.io.cbnualramischeduler.common.helper.fixture.CommunityCountFixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate

@DisplayName("커뮤니티 조회수 redis -> DB 통합 테스트")
class CountCommunityViewTest(
    private val communityCounter: CommunityCounter,
    private val communityCountJpaRepository: CommunityCountJpaRepository,
    private val communityCountFixture: CommunityCountFixture,
    private val redisTemplate: RedisTemplate<String, String>,
): TestContainerBase() {

    @Test
    fun `레디스에서 카운트를 조회하여 DB로 옴겨준다`() {
        val createCommunityCount = communityCountFixture.createCommunityCount()
        communityCounter.countCommunityViews()

        val communityCount = communityCountJpaRepository.findById(createCommunityCount.id!!).get()

        communityCount.count shouldBe 1L
    }

    @Test
    fun `레디스에서 DB로 카운트를 옴겨주면 레디스의 해당 community_count 키값은 사라진다`() {
        communityCountFixture.createCommunityCount()
        communityCounter.countCommunityViews()
        val values = redisTemplate.keys("$COMMUNITY_COUNT_KEY*")
        values.size shouldBe 0
    }
}
