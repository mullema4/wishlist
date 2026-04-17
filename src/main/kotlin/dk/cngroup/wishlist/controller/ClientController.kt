package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.service.ClientService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Clients", description = "CRUD and search operations for clients")
class ClientController(private val service: ClientService) {

    @GetMapping("/clients")
    @Operation(summary = "List clients")
    fun getClients(): List<ClientResponse> = service.getClients()

    @GetMapping("/clients/{id}")
    @Operation(summary = "Get a client by id")
    fun getClient(
        @Parameter(description = "Client identifier", example = "1")
        @PathVariable id: Long
    ): ClientResponse = service.getClient(id)

    @GetMapping("/clients/search/findByUserName")
    @Operation(summary = "Find a client by user name")
    fun getByName(
        @Parameter(description = "Derived username in upper-case FIRST_LAST format", example = "DARTH_VADER")
        @RequestParam userName: String
    ): ClientResponse = service.getByUserName(userName)

    @PostMapping("/clients")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a client")
    fun createClient(
        @Valid @RequestBody request: ClientRequest
    ): ClientResponse = service.createClient(request)

    @PutMapping("/clients/{id}")
    @Operation(summary = "Replace a client")
    fun replaceClient(
        @Parameter(description = "Client identifier", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody request: ClientRequest
    ): ClientResponse = service.replaceClient(id, request)

    @PatchMapping("/clients/{id}")
    @Operation(summary = "Partially update a client")
    fun updateClient(
        @Parameter(description = "Client identifier", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody request: ClientPatchRequest
    ): ClientResponse = service.updateClient(id, request)

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a client")
    fun deleteClient(
        @Parameter(description = "Client identifier", example = "1")
        @PathVariable id: Long
    ) = service.deleteClient(id)
}
