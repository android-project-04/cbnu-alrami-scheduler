package cbnu.io.cbnualramischeduler.business.web.crawling.application.service

import cbnu.io.cbnualramischeduler.business.core.domain.notification.entity.Notification
import cbnu.io.cbnualramischeduler.business.web.crawling.application.CbnuNotificationCrawler
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CbnuNotificationCrawlerService: CbnuNotificationCrawler {

    @Value("\${cbnu.url}")
    lateinit var url: String

    override fun crawlingNotification(): MutableList<Notification> {
        val conn = Jsoup.connect(url)
        val document = conn.get()
        val title = document.getElementsByClass("title")
        val notificationList: MutableList<Notification> = ArrayList()
        for (element in title) {

            val url = element.getElementsByTag("a").attr("href")
            val title = element.getElementsByTag("a").text()

            val notification = Notification(
                title = title,
                url = url,
            )

            notificationList.add(notification)
        }
        return notificationList;
    }
}
