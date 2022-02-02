package dk.cngroup.fetch.controller

import dk.cngroup.fetch.entity.Product
import dk.cngroup.fetch.entity.ProductRepository
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val repository: ProductRepository) {

    @PutMapping("/foo")
    fun foo(@RequestBody product: Product) {
        repository.save(product)
    }
}