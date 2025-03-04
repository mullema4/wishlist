package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.TransactionalService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val transactionalService: TransactionalService
) {
    @GetMapping("/test")
    fun foo() {
        transactionalService.processProducts()
    }
}