package dk.cngroup.fetch.controller;

import dk.cngroup.fetch.entity.Client;
import dk.cngroup.fetch.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping("/client-management")
public class ClientController {

	final ClientRepository repository;

	@GetMapping("/{name}")
	public ResponseEntity<PersistentEntityResource> getByName(
			@PathVariable String name,
			PersistentEntityResourceAssembler resourceAssembler) {
		Client client = repository.findClientByUsername(name);
		return ResponseEntity.ok(resourceAssembler.toFullResource(client));
	}
}