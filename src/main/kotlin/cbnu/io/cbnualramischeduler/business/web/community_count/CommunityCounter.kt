package cbnu.io.cbnualramischeduler.business.web.community_count

import cbnu.io.cbnualramischeduler.business.core.domain.community_count.infrastructure.CommunityCountJpaRepository
import cbnu.io.cbnualramischeduler.business.web.util.COMMUNITY_COUNT_KEY
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class CommunityCounter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val communityCountJpaRepository: CommunityCountJpaRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass.javaClass)

    @Transactional
    fun countCommunityViews() {
        val keys: MutableSet<String> = redisTemplate.keys("$COMMUNITY_COUNT_KEY*")

        for (key in keys) {
            val communityId = findCommunityIdByStringKey(key).toLong()
            val communityCount = communityCountJpaRepository.findByCommunityId(communityId)
                    ?: throw IllegalArgumentException("해당 community_id값의 community_count가 없습니다.")

            val stringCount = getStringCount(key)
            val count = stringCount.toLong()
            communityCount.changeCount(count)
        }
    }

    private fun getStringCount(key: String): String {

        val key_number = key.split(":").get(1)
        val stringCount = redisTemplate.opsForValue().get("$COMMUNITY_COUNT_KEY$key_number")
        if (stringCount == null) {
            throw IllegalArgumentException("Redis key값 오류입니다.")
        }
        redisTemplate.delete("$COMMUNITY_COUNT_KEY$key_number")
        return stringCount
    }

    private fun findCommunityIdByStringKey(key: String): String {
        logger.info("find community count id: $key")
        return key.split(":").get(1)
    }
}
