package dk.cngroup.wishlist

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulingService {

    // this function takes 10s to finish and would not start again in another thread each second unless @Async is used
    // @Async tasks are delegated to a different thread pool not blocking any @Scheduled tasks
    @Async
    @Scheduled(fixedRate = 1000)
    fun scheduledFunction() {
        for (i in 1..10) {
            println("${Thread.currentThread().name} | waiting for $i")
            Thread.sleep(1000)
        }
    }

    // all @Scheduled are sharing 1 thread by default and have to wait for others completion
    // use spring.task.scheduling.pool.size to allow parallel execution
    @Scheduled(fixedRate = 1000, initialDelay = 200)
    fun scheduledFunction2() {
        for (i in 1..10) {
            println("${Thread.currentThread().name} | waiting for $i")
            Thread.sleep(1000)
        }
    }
}