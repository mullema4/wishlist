package dk.cngroup.wishlist.service;

import dk.cngroup.wishlist.controller.dto.ClientPatchRequest;
import dk.cngroup.wishlist.controller.dto.ClientRequest;
import dk.cngroup.wishlist.controller.dto.ClientResponse;
import dk.cngroup.wishlist.entity.Client;
import dk.cngroup.wishlist.entity.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dk.cngroup.wishlist.controller.ControllerSupport.ifDefined;
import static dk.cngroup.wishlist.controller.ControllerSupport.resourceNotFound;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;

    @Transactional(readOnly = true)
    public List<ClientResponse> getClients() {
        return repository.findAll().stream().map(ClientResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse getClient(Long id) {
        return repository.findById(id)
                .map(ClientResponse::from)
                .orElseThrow(() -> resourceNotFound("client", id));
    }

    @Transactional(readOnly = true)
    public ClientResponse getByUserName(String userName) {
        Client client = repository.findClientByUserName(userName);
        if (client == null) {
            throw resourceNotFound("client", "userName " + userName);
        }
        return ClientResponse.from(client);
    }

    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        return ClientResponse.from(repository.saveAndFlush(
                new Client(request.getActive(), request.getFirstName(), request.getLastName())
        ));
    }

    @Transactional
    public ClientResponse replaceClient(Long id, ClientRequest request) {
        Client client = repository.findById(id)
                .orElseGet(() -> new Client(request.getActive(), request.getFirstName(), request.getLastName()));
        client.setActive(request.getActive());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        return ClientResponse.from(repository.saveAndFlush(client));
    }

    @Transactional
    public ClientResponse updateClient(Long id, ClientPatchRequest request) {
        Client client = repository.findById(id).orElseThrow(() -> resourceNotFound("client", id));
        ifDefined(request.getActive(), client::setActive);
        ifDefined(request.getFirstName(), client::setFirstName);
        ifDefined(request.getLastName(), client::setLastName);
        return ClientResponse.from(repository.saveAndFlush(client));
    }

    @Transactional
    public void deleteClient(Long id) {
        repository.deleteById(id);
    }
}
