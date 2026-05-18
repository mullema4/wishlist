package dk.cngroup.wishlist.controller;

import dk.cngroup.wishlist.controller.dto.ClientPatchRequest;
import dk.cngroup.wishlist.controller.dto.ClientRequest;
import dk.cngroup.wishlist.controller.dto.ClientResponse;
import dk.cngroup.wishlist.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Tag(name = "Clients", description = "CRUD and search operations for clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @GetMapping("/clients")
    @Operation(summary = "List clients")
    public List<ClientResponse> getClients() {
        return service.getClients();
    }

    @GetMapping("/clients/{id}")
    @Operation(summary = "Get a client by id")
    public ClientResponse getClient(
            @Parameter(description = "Client identifier", example = "1")
            @PathVariable Long id) {
        return service.getClient(id);
    }

    @GetMapping("/clients/search/findByUserName")
    @Operation(summary = "Find a client by user name")
    public ClientResponse getByName(
            @Parameter(description = "Derived username in upper-case FIRST_LAST format", example = "DARTH_VADER")
            @RequestParam String userName) {
        return service.getByUserName(userName);
    }

    @PostMapping("/clients")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a client")
    public ClientResponse createClient(@Valid @RequestBody ClientRequest request) {
        return service.createClient(request);
    }

    @PutMapping("/clients/{id}")
    @Operation(summary = "Replace a client")
    public ClientResponse replaceClient(
            @Parameter(description = "Client identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request) {
        return service.replaceClient(id, request);
    }

    @PatchMapping("/clients/{id}")
    @Operation(summary = "Partially update a client")
    public ClientResponse updateClient(
            @Parameter(description = "Client identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ClientPatchRequest request) {
        return service.updateClient(id, request);
    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a client")
    public void deleteClient(
            @Parameter(description = "Client identifier", example = "1")
            @PathVariable Long id) {
        service.deleteClient(id);
    }
}
