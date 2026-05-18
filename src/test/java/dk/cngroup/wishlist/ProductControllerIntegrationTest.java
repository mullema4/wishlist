package dk.cngroup.wishlist;

import dk.cngroup.wishlist.entity.Product;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerIntegrationTest extends SpringIntegrationTest {
    static final String CONTROLLER_PATH = "/products";

    @Test
    void getCollectionReturnsProducts() throws Exception {
        mockMvc.perform(get(CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", equalTo("TIE Fighter")));
    }

    @Test
    void getReturnsOneProduct() throws Exception {
        String productCode = "TIE Fighter";
        Product product = productRepository.findAll().stream()
                .filter(candidate -> productCode.equals(candidate.getCode()))
                .findFirst()
                .orElseThrow();

        mockMvc.perform(get(CONTROLLER_PATH + "/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(productCode)));
    }

    @Test
    void postCreatesProduct() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content("{\"code\":\"Executor\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", equalTo("Executor")));
    }

    @Test
    void putReplacesProduct() throws Exception {
        Product product = productRepository.saveAndFlush(new Product("Lambda Shuttle"));

        mockMvc.perform(put(CONTROLLER_PATH + "/" + product.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"code\":\"Imperial Shuttle\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("Imperial Shuttle")));
    }

    @Test
    void putCreatesProductWhenIdDoesNotExist() throws Exception {
        long missingId = 999999L;

        mockMvc.perform(put(CONTROLLER_PATH + "/" + missingId)
                        .contentType(APPLICATION_JSON)
                        .content("{\"code\":\"Imperial Shuttle\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code", equalTo("Imperial Shuttle")));
    }

    @Test
    void patchUpdatesProduct() throws Exception {
        Product product = productRepository.saveAndFlush(new Product("Executor"));

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + product.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"code\":\"Executor SSD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("Executor SSD")));
    }

    @Test
    void patchLeavesProductUnchangedWhenCodeIsOmitted() throws Exception {
        Product product = productRepository.saveAndFlush(new Product("Executor"));

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + product.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("Executor")));
    }

    @Test
    void patchRejectsExplicitNullProductCode() throws Exception {
        Product product = productRepository.saveAndFlush(new Product("Executor"));

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + product.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"code\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", equalTo("code")))
                .andExpect(jsonPath("$.errors[0].message", equalTo("must not be blank")));
    }

    @Test
    void deleteRemovesProductInIdempotentWay() throws Exception {
        Product product = productRepository.saveAndFlush(new Product("Naboo Starfighter"));

        mockMvc.perform(delete(CONTROLLER_PATH + "/" + product.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete(CONTROLLER_PATH + "/" + product.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(CONTROLLER_PATH + "/" + product.getId()))
                .andExpect(status().isNotFound());
    }
}
