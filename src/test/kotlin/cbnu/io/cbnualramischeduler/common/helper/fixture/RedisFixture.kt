package cbnu.io.cbnualramischeduler.common.helper.fixture

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisFixture(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    fun incrementByKey(key: String) {
        redisTemplate.opsForValue().increment(key)
    }
}
