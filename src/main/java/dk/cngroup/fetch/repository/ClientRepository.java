package dk.cngroup.fetch.repository;

import dk.cngroup.fetch.entity.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ClientRepository extends CrudRepository<Client, Long> {

	@RestResource(exported = false)
	Client getByUsername(String username);

	@RestResource(exported = false)
	@EntityGraph(attributePaths = {"wishes"})
	Client findByUsername(String username);

	@EntityGraph(attributePaths = {"wishes.products"})
	Client findClientByUsername(String username);
}