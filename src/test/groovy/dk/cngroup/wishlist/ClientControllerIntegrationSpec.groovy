package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.Client
import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.Wishlist

import static org.hamcrest.Matchers.equalTo
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ClientControllerIntegrationSpec extends SpringIntegrationSpec {

    static final CONTROLLER_PATH = '/clients'
    static final SEARCH_PATH = '/clients/search/findByUserName'

    def 'GET collection returns clients'() {
        when:
        def response = mockMvc.perform(get(CONTROLLER_PATH))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$[0].lastName', equalTo('Vader')))
    }

    def 'GET returns one client'() {
        given:
        def vader = clientRepository.getByUserName('DARTH_VADER')

        when:
        def response = mockMvc.perform(get("$CONTROLLER_PATH/${vader.id}"))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.userName', equalTo('DARTH_VADER')))
    }

    def 'Search by user name returns matching client'() {
        given:
        def wishes = new Wishlist(products: [new Product(code: 'Sith Infiltrator')])
        def maul = new Client(active: true, firstName: 'Darth', lastName: 'Maul', wishes: [wishes])
        clientRepository.saveAndFlush(maul)

        when:
        def response = mockMvc.perform(get(SEARCH_PATH).param('userName', 'DARTH_MAUL'))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.lastName', equalTo('Maul')))
    }

    def 'Search by user name returns 404 for missing client'() {
        when:
        def response = mockMvc.perform(get(SEARCH_PATH).param('userName', 'FOO'))

        then:
        response.andExpect(status().isNotFound())
    }

    def 'POST creates client'() {
        when:
        def response = mockMvc.perform(
                post(CONTROLLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content('{"active":true,"firstName":"Boba","lastName":"Fett"}')
        )

        then:
        response.andExpect(status().isCreated())
                .andExpect(jsonPath('$.userName', equalTo('BOBA_FETT')))
    }

    def 'PUT replaces client'() {
        given:
        def vader = clientRepository.getByUserName('DARTH_VADER')

        when:
        def response = mockMvc.perform(
                put("$CONTROLLER_PATH/${vader.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"active":true,"firstName":"Anakin","lastName":"Skywalker"}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.firstName', equalTo('Anakin')))
                .andExpect(jsonPath('$.userName', equalTo('ANAKIN_SKYWALKER')))
    }

    def 'PUT creates client when id does not exist'() {
        given:
        def missingId = 999998L

        when:
        def response = mockMvc.perform(
                put("$CONTROLLER_PATH/$missingId")
                        .contentType(APPLICATION_JSON)
                        .content('{"active":true,"firstName":"Anakin","lastName":"Skywalker"}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.firstName', equalTo('Anakin')))
                .andExpect(jsonPath('$.userName', equalTo('ANAKIN_SKYWALKER')))
    }

    def 'PATCH updates client'() {
        given:
        def vader = clientRepository.getByUserName('DARTH_VADER')

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${vader.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"lastName":"Ren"}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.lastName', equalTo('Ren')))
                .andExpect(jsonPath('$.userName', equalTo('DARTH_REN')))
    }

    def 'PATCH leaves client unchanged when fields are omitted'() {
        given:
        def vader = clientRepository.getByUserName('DARTH_VADER')

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${vader.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{}')
        )

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.firstName', equalTo('Darth')))
                .andExpect(jsonPath('$.lastName', equalTo('Vader')))
                .andExpect(jsonPath('$.userName', equalTo('DARTH_VADER')))
    }

    def 'PATCH rejects explicit null client last name'() {
        given:
        def vader = clientRepository.getByUserName('DARTH_VADER')

        when:
        def response = mockMvc.perform(
                patch("$CONTROLLER_PATH/${vader.id}")
                        .contentType(APPLICATION_JSON)
                        .content('{"lastName":null}')
        )

        then:
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errors[0].field', equalTo('lastName')))
                .andExpect(jsonPath('$.errors[0].message', equalTo('must not be blank')))
    }

    def 'DELETE removes client in idempotent way'() {
        given:
        def client = clientRepository.saveAndFlush(new Client(active: true, firstName: 'Count', lastName: 'Dooku'))

        when:
        def deleteResponse = mockMvc.perform(delete("$CONTROLLER_PATH/${client.id}"))
        def secondDeleteResponse = mockMvc.perform(delete("$CONTROLLER_PATH/${client.id}"))
        def getResponse = mockMvc.perform(get("$CONTROLLER_PATH/${client.id}"))

        then:
        deleteResponse.andExpect(status().isNoContent())
        secondDeleteResponse.andExpect(status().isNoContent())
        getResponse.andExpect(status().isNotFound())
    }
}
