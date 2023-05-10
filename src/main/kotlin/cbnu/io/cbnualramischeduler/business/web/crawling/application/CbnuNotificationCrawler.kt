package cbnu.io.cbnualramischeduler.business.web.crawling.application

import cbnu.io.cbnualramischeduler.business.core.domain.notification.Notification

interface CbnuNotificationCrawler {

    fun crawlingNotification(): MutableList<Notification>
}
