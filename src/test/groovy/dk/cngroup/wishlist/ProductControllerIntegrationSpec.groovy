package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.Product

import static org.hamcrest.Matchers.equalTo
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProductControllerIntegrationSpec extends SpringIntegrationSpec {

    static final CONTROLLER_PATH = '/products'

    def 'GET collection returns products'() {
        when:
        def response = mockMvc.perform(get(CONTROLLER_PATH))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].code', equalTo('TIE Fighter')))
    }

    def 'GET returns one product'() {
        given:
        def productCode = 'TIE Fighter'
        def product = productRepository.findAll().find { it.code == productCode }

        when:
        def response = mockMvc.perform(get("$CONTROLLER_PATH/${product.id}"))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.code', equalTo(productCode)))
    }

    def 'POST creates product'() {
        when:
        def response = mockMvc.perform(
                post(CONTROLLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content('{"code":"Executor"}')
        )

        then:
        response.andExpect(status().isCreated())
                .andExpect(jsonPath('$.code', equalTo('Executor')))
    }

    def 'Put replaces product'() {
        given:
        def product = productRepository.saveAndFlush(new Product(code: 'Lambda Shuttle'))

        when:
        def response = mockMvc.perform(
                put("$CONTROLLER_PATH/${product.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"code":"Imperial Shuttle"}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.code', equalTo('Imperial Shuttle')))
    }

    def 'PUT creates product when id does not exist'() {
        given:
        def missingId = 999999L

        when:
        def response = mockMvc.perform(
                put("$CONTROLLER_PATH/$missingId")
                        .contentType(APPLICATION_JSON)
                        .content('{"code":"Imperial Shuttle"}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.code', equalTo('Imperial Shuttle')))
    }

    def 'PATCH updates product'() {
        given:
        def product = productRepository.saveAndFlush(new Product(code: 'Executor'))

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${product.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"code":"Executor SSD"}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.code', equalTo('Executor SSD')))
    }

    def 'PATCH leaves product unchanged when code is omitted'() {
        given:
        def product = productRepository.saveAndFlush(new Product(code: 'Executor'))

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${product.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.code', equalTo('Executor')))
    }

    def 'PATCH rejects explicit null product code'() {
        given:
        def product = productRepository.saveAndFlush(new Product(code: 'Executor'))

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${product.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"code":null}')
        )

        then:
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errors[0].field', equalTo('code')))
                .andExpect(jsonPath('$.errors[0].message', equalTo('must not be blank')))
    }

    def 'DELETE removes product in idempotent way'() {
        given:
        def product = productRepository.saveAndFlush(new Product(code: 'Naboo Starfighter'))

        when:
        def deleteResponse = mockMvc.perform(delete("$CONTROLLER_PATH/${product.id}"))
        def secondDeleteResponse = mockMvc.perform(delete("$CONTROLLER_PATH/${product.id}"))
        def getResponse = mockMvc.perform(get("$CONTROLLER_PATH/${product.id}"))

        then:
        deleteResponse.andExpect(status().isNoContent())
        secondDeleteResponse.andExpect(status().isNoContent())
        getResponse.andExpect(status().isNotFound())
    }
}
