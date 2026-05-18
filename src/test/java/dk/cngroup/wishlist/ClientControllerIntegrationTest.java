package dk.cngroup.wishlist;

import dk.cngroup.wishlist.entity.Client;
import dk.cngroup.wishlist.entity.Product;
import dk.cngroup.wishlist.entity.Wishlist;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerIntegrationTest extends SpringIntegrationTest {
    static final String CONTROLLER_PATH = "/clients";
    static final String SEARCH_PATH = "/clients/search/findByUserName";

    @Test
    void getCollectionReturnsClients() throws Exception {
        mockMvc.perform(get(CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", equalTo("Vader")));
    }

    @Test
    void getReturnsOneClient() throws Exception {
        Client vader = clientRepository.getByUserName("DARTH_VADER");

        mockMvc.perform(get(CONTROLLER_PATH + "/" + vader.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", equalTo("DARTH_VADER")));
    }

    @Test
    void searchByUserNameReturnsMatchingClient() throws Exception {
        Wishlist wishes = new Wishlist(List.of(new Product("Sith Infiltrator")));
        Client maul = new Client(true, "Darth", "Maul", List.of(wishes));
        clientRepository.saveAndFlush(maul);

        mockMvc.perform(get(SEARCH_PATH).param("userName", "DARTH_MAUL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", equalTo("Maul")));
    }

    @Test
    void searchByUserNameReturnsNotFoundForMissingClient() throws Exception {
        mockMvc.perform(get(SEARCH_PATH).param("userName", "FOO"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postCreatesClient() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content("{\"active\":true,\"firstName\":\"Boba\",\"lastName\":\"Fett\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", equalTo("BOBA_FETT")));
    }

    @Test
    void putReplacesClient() throws Exception {
        Client vader = clientRepository.getByUserName("DARTH_VADER");

        mockMvc.perform(put(CONTROLLER_PATH + "/" + vader.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"active\":true,\"firstName\":\"Anakin\",\"lastName\":\"Skywalker\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Anakin")))
                .andExpect(jsonPath("$.userName", equalTo("ANAKIN_SKYWALKER")));
    }

    @Test
    void putCreatesClientWhenIdDoesNotExist() throws Exception {
        long missingId = 999998L;

        mockMvc.perform(put(CONTROLLER_PATH + "/" + missingId)
                        .contentType(APPLICATION_JSON)
                        .content("{\"active\":true,\"firstName\":\"Anakin\",\"lastName\":\"Skywalker\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName", equalTo("Anakin")))
                .andExpect(jsonPath("$.userName", equalTo("ANAKIN_SKYWALKER")));
    }

    @Test
    void patchUpdatesClient() throws Exception {
        Client vader = clientRepository.getByUserName("DARTH_VADER");

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + vader.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"lastName\":\"Ren\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", equalTo("Ren")))
                .andExpect(jsonPath("$.userName", equalTo("DARTH_REN")));
    }

    @Test
    void patchLeavesClientUnchangedWhenFieldsAreOmitted() throws Exception {
        Client vader = clientRepository.getByUserName("DARTH_VADER");

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + vader.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Darth")))
                .andExpect(jsonPath("$.lastName", equalTo("Vader")))
                .andExpect(jsonPath("$.userName", equalTo("DARTH_VADER")));
    }

    @Test
    void patchRejectsExplicitNullClientLastName() throws Exception {
        Client vader = clientRepository.getByUserName("DARTH_VADER");

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + vader.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"lastName\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", equalTo("lastName")))
                .andExpect(jsonPath("$.errors[0].message", equalTo("must not be blank")));
    }

    @Test
    void deleteRemovesClientInIdempotentWay() throws Exception {
        Client client = clientRepository.saveAndFlush(new Client(true, "Count", "Dooku"));

        mockMvc.perform(delete(CONTROLLER_PATH + "/" + client.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete(CONTROLLER_PATH + "/" + client.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(CONTROLLER_PATH + "/" + client.getId()))
                .andExpect(status().isNotFound());
    }
}
