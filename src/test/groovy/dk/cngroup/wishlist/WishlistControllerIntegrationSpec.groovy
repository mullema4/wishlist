package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.Wishlist

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.nullValue
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WishlistControllerIntegrationSpec extends SpringIntegrationSpec {

    static final CONTROLLER_PATH = '/wishlists'

    def 'GET collection returns wishlists'() {
        when:
        def response = mockMvc.perform(get(CONTROLLER_PATH))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].products[0].code', equalTo('TIE Fighter')))
    }

    def 'GET returns one wishlist'() {
        given:
        def wishlist = wishlistRepository.findAll().first()

        when:
        def response = mockMvc.perform(get("$CONTROLLER_PATH/${wishlist.id}"))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.products[1].code', equalTo('Death Star')))
    }

    def 'POST creates wishlist'() {
        given:
        def client = clientRepository.getByUserName('DARTH_VADER')
        def product = productRepository.saveAndFlush(new Product(code: 'Lambda Shuttle'))

        when:
        def response = mockMvc.perform(
                post(CONTROLLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content("""{"clientId":${client.id},"productIds":[${product.id}]}""")
        )

        then:
        response.andExpect(status().isCreated())
                .andExpect(jsonPath('$.clientId', equalTo(client.id.intValue())))
                .andExpect(jsonPath('$.products[0].code', equalTo('Lambda Shuttle')))
    }

    def 'PUT replaces wishlist'() {
        given:
        def wishlist = wishlistRepository.findAll().first()
        def client = clientRepository.getByUserName('DARTH_VADER')
        def product = productRepository.saveAndFlush(new Product(code: 'Imperial Probe Droid'))

        when:
        def response = mockMvc.perform(
                put("$CONTROLLER_PATH/${wishlist.id}")
                        .contentType(APPLICATION_JSON)
                        .content("""{"clientId":${client.id},"productIds":[${product.id}]}""")
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.products[0].code', equalTo('Imperial Probe Droid')))
    }

    def 'PUT creates wishlist when id does not exist'() {
        given:
        def missingId = 999997L
        def client = clientRepository.getByUserName('DARTH_VADER')
        def product = productRepository.saveAndFlush(new Product(code: 'Imperial Probe Droid'))

        when:
        def response = mockMvc.perform(
                put("$CONTROLLER_PATH/$missingId")
                        .contentType(APPLICATION_JSON)
                        .content("""{"clientId":${client.id},"productIds":[${product.id}]}""")
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.clientId', equalTo(client.id.intValue())))
                .andExpect(jsonPath('$.products[0].code', equalTo('Imperial Probe Droid')))
    }

    def 'PATCH updates wishlist'() {
        given:
        def wishlist = wishlistRepository.findAll().first()
        def firstProduct = productRepository.saveAndFlush(new Product(code: 'Lambda Shuttle'))
        def secondProduct = productRepository.saveAndFlush(new Product(code: 'Imperial Probe Droid'))

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${wishlist.id}")
                        .contentType(APPLICATION_JSON)
                        .content("""{"productIds":[${firstProduct.id},${secondProduct.id}]}""")
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.products[0].code', equalTo('Lambda Shuttle')))
                .andExpect(jsonPath('$.products[1].code', equalTo('Imperial Probe Droid')))
    }

    def 'PATCH leaves wishlist unchanged when fields are omitted'() {
        given:
        def wishlist = wishlistRepository.findAll().first()
        def originalClientId = wishlist.client.id

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${wishlist.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.clientId', equalTo(originalClientId.intValue())))
                .andExpect(jsonPath('$.products[0].code', equalTo('TIE Fighter')))
                .andExpect(jsonPath('$.products[1].code', equalTo('Death Star')))
    }

    def 'PATCH nullifies wishlist client when clientId is null'() {
        given:
        def wishlist = wishlistRepository.findAll().first()

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${wishlist.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"clientId":null}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.clientId', nullValue()))
                .andExpect(jsonPath('$.products[0].code', equalTo('TIE Fighter')))
    }

    def 'PATCH rejects explicit null productIds'() {
        given:
        def wishlist = wishlistRepository.findAll().first()

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${wishlist.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"productIds":null}')
        )

        then:
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errors[0].field', equalTo('productIds')))
                .andExpect(jsonPath('$.errors[0].message', equalTo('must not be null')))
    }

    def 'DELETE removes wishlist in idempotent way'() {
        given:
        def client = clientRepository.getByUserName('DARTH_VADER')
        def wishlist = wishlistRepository.saveAndFlush(new Wishlist(client: client))

        when:
        def deleteResponse = mockMvc.perform(delete("$CONTROLLER_PATH/${wishlist.id}"))
        def secondDeleteResponse = mockMvc.perform(delete("$CONTROLLER_PATH/${wishlist.id}"))
        def getResponse = mockMvc.perform(get("$CONTROLLER_PATH/${wishlist.id}"))

        then:
        deleteResponse.andExpect(status().isNoContent())
        secondDeleteResponse.andExpect(status().isNoContent())
        getResponse.andExpect(status().isNotFound())
    }
}
