package dk.cngroup.fetch.repository;

import dk.cngroup.fetch.entity.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @RestResource(exported = false)
    Client getByUserName(String userName);

    @RestResource(exported = false)
    @EntityGraph(attributePaths = {"wishes"})
    Client findByUserName(String userName);

    @EntityGraph(attributePaths = {"wishes.products"})
    Client findClientByUserName(String userName);
}