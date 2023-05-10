package cbnu.io.cbnualramischeduler.business.web.scheduler.application.service

import cbnu.io.cbnualramischeduler.business.web.notification.application.NotificationWriteCommand
import cbnu.io.cbnualramischeduler.business.web.scheduler.application.NotificationScheduler
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationSchedulerService(
    val notificationWriteCommand: NotificationWriteCommand,
): NotificationScheduler {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Async
    @Scheduled(cron = "* 0/30 * * * ?")
    override fun scheduleNotification() {
        log.info("=========================스케줄러 시작=========================")
        notificationWriteCommand.scheduleSaveTask()
    }
}
