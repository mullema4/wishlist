package dk.cngroup.wishlist.controller;

import dk.cngroup.wishlist.entity.Product;
import dk.cngroup.wishlist.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//classic Spring MVC controller
@RequiredArgsConstructor
@RestController
public class ProductController {
    final ProductRepository repository;

    @PostMapping("/product")
    public Product saveProduct(@Validated @RequestBody Product product) {
        return repository.save(product);
    }
}