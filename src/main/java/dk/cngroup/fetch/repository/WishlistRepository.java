package dk.cngroup.fetch.repository;

import dk.cngroup.fetch.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}