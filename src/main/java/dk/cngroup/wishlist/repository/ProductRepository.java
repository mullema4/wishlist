package dk.cngroup.wishlist.repository;

import dk.cngroup.wishlist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}