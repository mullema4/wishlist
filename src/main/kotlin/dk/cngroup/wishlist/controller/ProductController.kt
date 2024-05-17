package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.ProductRepository
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//classic Spring MVC controller
@RestController
class ProductController(private val repository: ProductRepository) {


    @GetMapping("/products/search/findByDescription")
    fun getProductsByDescription(@RequestParam description: String): List<Product> {
        return repository.findByDescriptionContaining(description)
    }


    @PostMapping("/product")
    fun saveProduct(@Validated @RequestBody product: Product): Product {
        return repository.save(product)
    }
}