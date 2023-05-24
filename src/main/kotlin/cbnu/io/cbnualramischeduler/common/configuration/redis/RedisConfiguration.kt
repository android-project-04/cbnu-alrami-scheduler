package cbnu.io.cbnualramischeduler.common.configuration.redis

import io.lettuce.core.ReadFrom
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration {

    @Value("\${spring.redis.host}")
    lateinit var host: String

    @Value("\${spring.redis.port}")
    lateinit var port: String

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        val clientConfig = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build()
        val serverConfig = RedisStandaloneConfiguration(host, Integer.parseInt(port))
        return LettuceConnectionFactory(serverConfig, clientConfig)
    }

    @Bean(name = ["redisTemplate"])
    fun objectRedisTemplate(): RedisTemplate<String, Any>? {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.setConnectionFactory(redisConnectionFactory()!!)
        return redisTemplate
    }
}
