package dk.cngroup.fetch.repository;

import dk.cngroup.fetch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}