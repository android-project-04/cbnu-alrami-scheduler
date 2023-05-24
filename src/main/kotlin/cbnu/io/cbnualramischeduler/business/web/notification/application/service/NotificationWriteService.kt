package cbnu.io.cbnualramischeduler.business.web.notification.application.service

import cbnu.io.cbnualramischeduler.business.core.domain.notification.entity.Notification
import cbnu.io.cbnualramischeduler.business.core.domain.notification.infrastructure.NotificationJpaRepository
import cbnu.io.cbnualramischeduler.business.web.crawling.application.CbnuNotificationCrawler
import cbnu.io.cbnualramischeduler.business.web.notification.application.NotificationWriteCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationWriteService(
    val notificationJpaRepository: NotificationJpaRepository,
    val cbnuNotificationCrawler: CbnuNotificationCrawler,
): NotificationWriteCommand {

    @Transactional
    override fun scheduleSaveTask() {
        val crawlingNotification: MutableList<Notification> = cbnuNotificationCrawler.crawlingNotification()

        for (notification in crawlingNotification) {
            saveIfNotExistUrl(notification)
        }

    }

    private fun saveIfNotExistUrl(notification: Notification) {
        val existsByUrl = notificationJpaRepository.existsByUrl(notification.url)
        if (!existsByUrl) {
            notificationJpaRepository.save(notification)
        }
    }
}
