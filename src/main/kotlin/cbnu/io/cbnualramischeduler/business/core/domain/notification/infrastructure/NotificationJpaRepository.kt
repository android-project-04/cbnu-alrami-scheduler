package cbnu.io.cbnualramischeduler.business.core.domain.notification.infrastructure

import cbnu.io.cbnualramischeduler.business.core.domain.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationJpaRepository : JpaRepository<Notification, Long>{

    fun existsByUrl(url: String): Boolean
}
