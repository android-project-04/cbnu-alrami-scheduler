package cbnu.io.cbnualramischeduler.common.rdb

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.shaded.com.google.common.base.CaseFormat
import java.util.stream.Collectors.toList
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class DatabaseCleanup: InitializingBean{

    @PersistenceContext
    lateinit var entityManager: EntityManager

    lateinit var tableNames: MutableList<String>

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities.stream()
            .filter { e -> e.javaType.getAnnotation(Entity::class.java) != null }
            .map { e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.name) }
            .collect(toList())
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.clear()

        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }
    }

}
