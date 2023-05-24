package cbnu.io.cbnualramischeduler.business.web.scheduler.application.service

import cbnu.io.cbnualramischeduler.business.web.notification.application.NotificationWriteCommand
import cbnu.io.cbnualramischeduler.business.web.scheduler.application.NotificationScheduler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationSchedulerService(
    val notificationWriteCommand: NotificationWriteCommand,
): NotificationScheduler {

//    private val log = LoggerFactory.getLogger(this.javaClass)
//    private val log = KotlinLogging.logger {}



    @Scheduled(cron = "0 0/30 * * * ?")
    override fun scheduleNotification() {
//        log.info("=========================스케줄러 시작=========================")
        notificationWriteCommand.scheduleSaveTask()
    }
}
