package dk.cngroup.wishlist

import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.retry.support.RetrySynchronizationManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.net.ConnectException


@Service
class RetryService {

    // will be invoked once 100ms after startup
    // second execution +10s, third +20s, then maxAttempts is reached and @Recover is called (exception type must match)
    @Scheduled(initialDelay = 100)
    @Retryable(
        retryFor = [ConnectException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 10000, multiplier = 2.0)
    )
    fun retryFunction() {
        val attemptNumber = RetrySynchronizationManager.getContext()?.retryCount
        println("${Thread.currentThread().name} | Retry attempt $attemptNumber")
        callThatCouldFail()
    }

    fun callThatCouldFail() {
        Thread.sleep(2000)
        throw ConnectException("Network is down")
    }

    @Recover
    fun recoverFunction(exception: ConnectException) {
        println("Remote stuff failed: ${exception.message}")
    }
}
