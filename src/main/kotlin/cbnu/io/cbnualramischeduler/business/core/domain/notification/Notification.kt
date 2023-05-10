package cbnu.io.cbnualramischeduler.business.core.domain.notification

import cbnu.io.cbnualramischeduler.business.core.domain.notification.value.DateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "notification")
@EntityListeners(AuditingEntityListener::class)
class Notification(
    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title")
    val title: String,

    @Column(name = "url")
    val url: String,

): DateTime() {

}
