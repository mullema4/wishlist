package dk.cngroup.fetch.repository;

import dk.cngroup.fetch.entity.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

	Client getByUsername(String username);

	@EntityGraph(attributePaths = {"wishes"})
	Client findByUsername(String username);

	@EntityGraph(value = "I-want-it-all")
	Client findClientByUsername(String username);
}