package dk.cngroup.wishlist.service;

import dk.cngroup.wishlist.controller.dto.ProductPatchRequest;
import dk.cngroup.wishlist.controller.dto.ProductRequest;
import dk.cngroup.wishlist.controller.dto.ProductResponse;
import dk.cngroup.wishlist.entity.Product;
import dk.cngroup.wishlist.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dk.cngroup.wishlist.controller.ControllerSupport.ifDefined;
import static dk.cngroup.wishlist.controller.ControllerSupport.resourceNotFound;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        return repository.findAll().stream().map(ProductResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return repository.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> resourceNotFound("product", id));
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        return ProductResponse.from(repository.saveAndFlush(new Product(request.code())));
    }

    @Transactional
    public ProductResponse replaceProduct(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseGet(() -> new Product(request.code()));
        product.setCode(request.code());
        return ProductResponse.from(repository.saveAndFlush(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductPatchRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> resourceNotFound("product", id));
        ifDefined(request.getCode(), product::setCode);
        return ProductResponse.from(repository.saveAndFlush(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
