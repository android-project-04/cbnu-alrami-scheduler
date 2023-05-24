package cbnu.io.cbnualramischeduler.common.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Flux

@Component
@ActiveProfiles("test")
class RedisInitialization {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    fun initRedisTemplate() {
        val keys = redisTemplate.keys("*")

        keys.map {
            x ->
            redisTemplate.delete(x)
            println("--------------------> delete redis key $x")
        }
    }
}
