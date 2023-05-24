package cbnu.io.cbnualramischeduler.business.core.domain.notification.entity.value

import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class DateTime(


    @Column(name = "created_at")
    var createdAt: ZonedDateTime? = ZonedDateTime.now(ZoneId.of("Asia/Seoul")),

    @Column(name = "last_modified_at")
    var lastModifiedAt: ZonedDateTime? = ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
) {

    @PrePersist
    fun prePersist() {
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        this.lastModifiedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }

    @PreUpdate
    fun preUpdate() {
        this.lastModifiedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}
