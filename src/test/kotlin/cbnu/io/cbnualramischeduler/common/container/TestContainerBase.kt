package cbnu.io.cbnualramischeduler.common.container

import cbnu.io.cbnualramischeduler.common.annotation.IntegrationTest
import cbnu.io.cbnualramischeduler.common.rdb.DatabaseCleanup
import cbnu.io.cbnualramischeduler.common.redis.RedisInitialization
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@IntegrationTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ContextConfiguration(initializers = [TestContainerBase.DatasourceInitializer::class])
abstract class TestContainerBase {

    class DatasourceInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        private val REDIS_IMAGE_NAME = DockerImageName.parse("redis:5.0.3-alpine")

        @Container
        private val redisContainer: GenericContainer<*> = GenericContainer(REDIS_IMAGE_NAME)
            .withExposedPorts(6379)
            .withReuse(true)

        @Container
        private val mySQLContainer = MySQLContainer("mysql:latest")

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            mySQLContainer.start()
            redisContainer.start()
            TestPropertyValues.of(
                "spring.datasource.url=${mySQLContainer.jdbcUrl}",
                "spring.datasource.username=${mySQLContainer.username}",
                "spring.datasource.password=${mySQLContainer.password}",

                "spring.redis.host=${redisContainer.host}",
                "spring.redis.port=${redisContainer.firstMappedPort}"
            ).applyTo(applicationContext.environment)
        }
    }

    @Autowired
    lateinit var databaseCleanup: DatabaseCleanup

    @Autowired
    lateinit var redisInitialization: RedisInitialization

    @BeforeEach
    fun setUp() {
        databaseCleanup.execute()
        redisInitialization.initRedisTemplate()
    }
}
