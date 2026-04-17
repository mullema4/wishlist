package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Products", description = "CRUD operations for products")
class ProductController(private val service: ProductService) {

    @GetMapping("/products")
    @Operation(summary = "List products")
    fun getProducts(): List<ProductResponse> = service.getProducts()

    @GetMapping("/products/{id}")
    @Operation(summary = "Get a product by id")
    fun getProduct(
        @Parameter(description = "Product identifier", example = "1")
        @PathVariable id: Long
    ): ProductResponse = service.getProduct(id)

    @PostMapping("/products")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a product")
    fun createProduct(
        @Valid @RequestBody request: ProductRequest
    ): ProductResponse = service.createProduct(request)

    @PutMapping("/products/{id}")
    @Operation(summary = "Replace a product")
    fun replaceProduct(
        @Parameter(description = "Product identifier", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody request: ProductRequest
    ): ProductResponse = service.replaceProduct(id, request)

    @PatchMapping("/products/{id}")
    @Operation(summary = "Partially update a product")
    fun updateProduct(
        @Parameter(description = "Product identifier", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody request: ProductPatchRequest
    ): ProductResponse = service.updateProduct(id, request)

    @DeleteMapping("/products/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a product")
    fun deleteProduct(
        @Parameter(description = "Product identifier", example = "1")
        @PathVariable id: Long
    ) = service.deleteProduct(id)
}
