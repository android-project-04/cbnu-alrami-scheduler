package cbnu.io.cbnualramischeduler.test.integration.notification

import cbnu.io.cbnualramischeduler.business.core.domain.notification.Notification
import cbnu.io.cbnualramischeduler.business.core.domain.notification.infrastructure.NotificationJpaRepository
import cbnu.io.cbnualramischeduler.business.web.crawling.application.CbnuNotificationCrawler
import cbnu.io.cbnualramischeduler.business.web.notification.application.service.NotificationWriteService
import cbnu.io.cbnualramischeduler.common.container.DatabaseTestBase
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("공지 스케줄러 쓰기 테스트")
@ExtendWith(MockKExtension::class)
internal class NotificationWriteTest: DatabaseTestBase() {

    @MockK
    lateinit var notificationCrawler: CbnuNotificationCrawler

    @Autowired
    lateinit var notificationJpaRepository: NotificationJpaRepository

    @InjectMockKs
    lateinit var notificationWriteService: NotificationWriteService


    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `새로운 공지가 올라오면 해당 공지를 저장한다`() {

        // 3개의 공지가 있다.
        every { notificationCrawler.crawlingNotification() } returns create3MutableListOfNotification()

        // 3개의 새로운 공지를 저장한다.
        notificationWriteService.scheduleSaveTask()

        // 3개중 하나의 공지를 삭제한다. -> 공지가 2개가 된다.
        notificationJpaRepository.deleteById(1L)

        // 스크롤링 후 다시 새로운 공지가 있으면 저장한다. -> 하나의 공지가 새로운 공지이다.
        notificationWriteService.scheduleSaveTask()

        // 2개의 공지가 새로운 공지를 저장해서 3개의 공지가 된다.
        notificationJpaRepository.findAll().size shouldBe 3
    }

    private fun create3MutableListOfNotification(): MutableList<Notification> {
        val notification1 = Notification(
            title = "test1",
            url = "url1"
        )
        val notification2 = Notification(
            title = "test2",
            url = "url2"
        )
        val notification3 = Notification(
            title = "test3",
            url = "url3"
        )

        return mutableListOf(notification1, notification2, notification3)
    }
}
