package cbnu.io.cbnualramischeduler.common.container

import cbnu.io.cbnualramischeduler.common.annotation.IntegrationTest
import cbnu.io.cbnualramischeduler.common.rdb.DatabaseCleanup
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@IntegrationTest
abstract class DatabaseTestBase {

    class DataSourceInitializer: ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Container
        private val database = MySQLContainer("mysql:latest")

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            database.start()
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + database.getJdbcUrl(),
                "spring.datasource.username=" + database.getUsername(),
                "spring.datasource.password=" + database.getPassword()
            )
        }
    }

    @Autowired
    lateinit var databaseCleanup: DatabaseCleanup

    @BeforeEach
    fun cleanDB() {
        databaseCleanup.execute()
    }
}
