package dk.cngroup.wishlist.entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client getByUserName(String userName);

    @EntityGraph(attributePaths = {"wishes"})
    Client findByUserName(String userName);

    @EntityGraph(attributePaths = {"wishes.products"})
    Client findClientByUserName(String userName);
}
