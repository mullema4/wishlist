package dk.cngroup.wishlist.controller;

import dk.cngroup.wishlist.controller.dto.ProductPatchRequest;
import dk.cngroup.wishlist.controller.dto.ProductRequest;
import dk.cngroup.wishlist.controller.dto.ProductResponse;
import dk.cngroup.wishlist.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Tag(name = "Products", description = "CRUD operations for products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/products")
    @Operation(summary = "List products")
    public List<ProductResponse> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "Get a product by id")
    public ProductResponse getProduct(
            @Parameter(description = "Product identifier", example = "1")
            @PathVariable Long id) {
        return service.getProduct(id);
    }

    @PostMapping("/products")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a product")
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return service.createProduct(request);
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "Replace a product")
    public ProductResponse replaceProduct(
            @Parameter(description = "Product identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return service.replaceProduct(id, request);
    }

    @PatchMapping("/products/{id}")
    @Operation(summary = "Partially update a product")
    public ProductResponse updateProduct(
            @Parameter(description = "Product identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductPatchRequest request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a product")
    public void deleteProduct(
            @Parameter(description = "Product identifier", example = "1")
            @PathVariable Long id) {
        service.deleteProduct(id);
    }
}
