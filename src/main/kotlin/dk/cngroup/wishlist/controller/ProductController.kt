package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.ProductRepository
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

//classic Spring MVC controller
@RestController
class ProductController(private val repository: ProductRepository) {

    @PostMapping("/product")
    fun saveProduct(@Validated @RequestBody product: Product): Product {
        return repository.save(product)
    }
}