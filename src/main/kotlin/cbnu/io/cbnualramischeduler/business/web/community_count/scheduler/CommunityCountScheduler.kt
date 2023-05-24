package cbnu.io.cbnualramischeduler.business.web.community_count.scheduler

import cbnu.io.cbnualramischeduler.business.web.community_count.CommunityCounter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CommunityCountScheduler(
    val communityCounter: CommunityCounter,
) {

//    private val log = LoggerFactory.getLogger(this.javaClass)
//    private val log = KotlinLogging.logger {}

    @Scheduled(cron = "0 0/1 * * * ?")
    fun scheduleCommunityCount() {
//        log.info("------------- community count scheduler start -------------")
        communityCounter.countCommunityViews()
    }
}
