package dk.cngroup.fetch.controller;

import dk.cngroup.fetch.entity.Product;
import dk.cngroup.fetch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//classic Spring MVC controller
@RequiredArgsConstructor
@RestController
public class ProductController {
    final ProductRepository repository;

    @PostMapping("/product")
    public Product saveProduct(@RequestBody Product product) {
        return repository.save(product);
    }
}