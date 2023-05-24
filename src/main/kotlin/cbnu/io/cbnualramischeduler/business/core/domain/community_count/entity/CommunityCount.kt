package cbnu.io.cbnualramischeduler.business.core.domain.community_count.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "community_count")
class CommunityCount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_count_id")
    var id: Long? = null,

    @Column(name = "community_count")
    var count: Long = 0L,

    @Column(name = "community_id")
    val communityId: Long
) {

    fun changeCount(count: Long): Long {
        this.count += count
        return this.count
    }
}
