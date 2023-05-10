package cbnu.io.cbnualramischeduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class CbnuAlramiSchedulerApplication

fun main(args: Array<String>) {
	runApplication<CbnuAlramiSchedulerApplication>(*args)
}
