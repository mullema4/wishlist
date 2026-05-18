package dk.cngroup.wishlist;

import dk.cngroup.wishlist.entity.Client;
import dk.cngroup.wishlist.entity.Product;
import dk.cngroup.wishlist.entity.Wishlist;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WishlistControllerIntegrationTest extends SpringIntegrationTest {
    static final String CONTROLLER_PATH = "/wishlists";

    @Test
    void getCollectionReturnsWishlists() throws Exception {
        mockMvc.perform(get(CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].products[0].code", equalTo("TIE Fighter")));
    }

    @Test
    void getReturnsOneWishlist() throws Exception {
        Wishlist wishlist = wishlistRepository.findAll().getFirst();

        mockMvc.perform(get(CONTROLLER_PATH + "/" + wishlist.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[1].code", equalTo("Death Star")));
    }

    @Test
    void postCreatesWishlist() throws Exception {
        Client client = clientRepository.getByUserName("DARTH_VADER");
        Product product = productRepository.saveAndFlush(new Product("Lambda Shuttle"));

        mockMvc.perform(post(CONTROLLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content("{\"clientId\":%d,\"productIds\":[%d]}".formatted(client.getId(), product.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId", equalTo(client.getId().intValue())))
                .andExpect(jsonPath("$.products[0].code", equalTo("Lambda Shuttle")));
    }

    @Test
    void putReplacesWishlist() throws Exception {
        Wishlist wishlist = wishlistRepository.findAll().getFirst();
        Client client = clientRepository.getByUserName("DARTH_VADER");
        Product product = productRepository.saveAndFlush(new Product("Imperial Probe Droid"));

        mockMvc.perform(put(CONTROLLER_PATH + "/" + wishlist.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"clientId\":%d,\"productIds\":[%d]}".formatted(client.getId(), product.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].code", equalTo("Imperial Probe Droid")));
    }

    @Test
    void putCreatesWishlistWhenIdDoesNotExist() throws Exception {
        long missingId = 999997L;
        Client client = clientRepository.getByUserName("DARTH_VADER");
        Product product = productRepository.saveAndFlush(new Product("Imperial Probe Droid"));

        mockMvc.perform(put(CONTROLLER_PATH + "/" + missingId)
                        .contentType(APPLICATION_JSON)
                        .content("{\"clientId\":%d,\"productIds\":[%d]}".formatted(client.getId(), product.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.clientId", equalTo(client.getId().intValue())))
                .andExpect(jsonPath("$.products[0].code", equalTo("Imperial Probe Droid")));
    }

    @Test
    void patchUpdatesWishlist() throws Exception {
        Wishlist wishlist = wishlistRepository.findAll().getFirst();
        Product firstProduct = productRepository.saveAndFlush(new Product("Lambda Shuttle"));
        Product secondProduct = productRepository.saveAndFlush(new Product("Imperial Probe Droid"));

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + wishlist.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"productIds\":[%d,%d]}".formatted(firstProduct.getId(), secondProduct.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].code", equalTo("Lambda Shuttle")))
                .andExpect(jsonPath("$.products[1].code", equalTo("Imperial Probe Droid")));
    }

    @Test
    void patchLeavesWishlistUnchangedWhenFieldsAreOmitted() throws Exception {
        Wishlist wishlist = wishlistRepository.findAll().getFirst();
        Long originalClientId = wishlist.getClient().getId();

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + wishlist.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", equalTo(originalClientId.intValue())))
                .andExpect(jsonPath("$.products[0].code", equalTo("TIE Fighter")))
                .andExpect(jsonPath("$.products[1].code", equalTo("Death Star")));
    }

    @Test
    void patchNullifiesWishlistClientWhenClientIdIsNull() throws Exception {
        Wishlist wishlist = wishlistRepository.findAll().getFirst();

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + wishlist.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"clientId\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", nullValue()))
                .andExpect(jsonPath("$.products[0].code", equalTo("TIE Fighter")));
    }

    @Test
    void patchRejectsExplicitNullProductIds() throws Exception {
        Wishlist wishlist = wishlistRepository.findAll().getFirst();

        mockMvc.perform(patch(CONTROLLER_PATH + "/" + wishlist.getId())
                        .contentType(APPLICATION_JSON)
                        .content("{\"productIds\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", equalTo("productIds")))
                .andExpect(jsonPath("$.errors[0].message", equalTo("must not be null")));
    }

    @Test
    void deleteRemovesWishlistInIdempotentWay() throws Exception {
        Client client = clientRepository.getByUserName("DARTH_VADER");
        Wishlist wishlist = wishlistRepository.saveAndFlush(new Wishlist(client, List.of()));

        mockMvc.perform(delete(CONTROLLER_PATH + "/" + wishlist.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete(CONTROLLER_PATH + "/" + wishlist.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(CONTROLLER_PATH + "/" + wishlist.getId()))
                .andExpect(status().isNotFound());
    }
}
