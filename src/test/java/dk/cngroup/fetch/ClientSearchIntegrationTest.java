package dk.cngroup.fetch;

import dk.cngroup.fetch.entity.Client;
import dk.cngroup.fetch.entity.Product;
import dk.cngroup.fetch.entity.Wishlist;
import dk.cngroup.fetch.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ClientSearchIntegrationTest {

    private static final String CONTROLLER_PATH = "/clients/search/findByUserName";

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void ExpectedJSONResponseIsCreatedForValidRequest() throws Exception {
        Product sithInfiltrator = new Product();
        sithInfiltrator.setCode("Sith Infiltrator");

        Wishlist wishes = new Wishlist();
        wishes.setProducts(List.of(sithInfiltrator));

        Client maul = new Client();
        maul.setFirstName("Darth");
        maul.setLastName("Maul");
        maul.setActive(true);
        maul.setWishes(List.of(wishes));

        clientRepository.saveAndFlush(maul);

        ResultActions response = mockMvc.perform(get(CONTROLLER_PATH).param("userName", "DARTH_MAUL"));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", equalTo("Maul")));
    }
}
