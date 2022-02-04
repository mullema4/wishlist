package dk.cngroup.fetch.controller

import dk.cngroup.fetch.entity.Product
import dk.cngroup.fetch.entity.ProductRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

//classic Spring MVC controller
@RestController
class ProductController(private val repository: ProductRepository) {

    @PostMapping("/product")
    fun saveProduct(@RequestBody product: Product): Product {
        return repository.save(product)
    }
}