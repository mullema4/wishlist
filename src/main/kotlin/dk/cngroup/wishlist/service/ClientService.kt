package dk.cngroup.wishlist.service

import dk.cngroup.wishlist.controller.*
import dk.cngroup.wishlist.entity.Client
import dk.cngroup.wishlist.entity.ClientRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClientService(private val repository: ClientRepository) {

    @Transactional(readOnly = true)
    fun getClients(): List<ClientResponse> =
        repository.findAll().map(Client::toResponse)

    @Transactional(readOnly = true)
    fun getClient(id: Long): ClientResponse =
        repository.findByIdOrNull(id)?.toResponse() ?: throw resourceNotFound("client", id)

    @Transactional(readOnly = true)
    fun getByUserName(userName: String): ClientResponse =
        repository.findClientByUserName(userName).toResponse()

    @Transactional
    fun createClient(request: ClientRequest): ClientResponse =
        repository.saveAndFlush(
            Client(
                active = request.active,
                firstName = request.firstName,
                lastName = request.lastName
            )
        ).toResponse()

    @Transactional
    fun replaceClient(id: Long, request: ClientRequest): ClientResponse {
        val client = repository.findByIdOrNull(id) ?: return repository.saveAndFlush(
            Client(
                active = request.active,
                firstName = request.firstName,
                lastName = request.lastName
            )
        ).toResponse()
        client.active = request.active
        client.firstName = request.firstName
        client.lastName = request.lastName
        return repository.saveAndFlush(client).toResponse()
    }

    @Transactional
    fun updateClient(id: Long, request: ClientPatchRequest): ClientResponse {
        val client = repository.findByIdOrNull(id) ?: throw resourceNotFound("client", id)
        request.active.ifDefined { client.active = it }
        request.firstName.ifDefined { client.firstName = it }
        request.lastName.ifDefined { client.lastName = it }
        return repository.saveAndFlush(client).toResponse()
    }

    @Transactional
    fun deleteClient(id: Long) = repository.deleteById(id)
}
