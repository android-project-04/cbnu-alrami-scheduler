package cbnu.io.cbnualramischeduler.business.core.domain.notification.entity

import cbnu.io.cbnualramischeduler.business.core.domain.notification.entity.value.DateTime
import org.springframework.data.jpa.domain.support.AuditingEntityListener
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
