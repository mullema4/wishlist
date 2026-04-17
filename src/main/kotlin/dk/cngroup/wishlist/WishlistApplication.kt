package dk.cngroup.wishlist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class WishlistApplication

fun main(args: Array<String>) {
    runApplication<WishlistApplication>(*args)
}
