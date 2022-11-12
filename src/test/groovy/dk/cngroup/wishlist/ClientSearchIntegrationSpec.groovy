package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.Client
import dk.cngroup.wishlist.entity.ClientRepository
import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.Wishlist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.transaction.Transactional

import static org.hamcrest.Matchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ClientSearchIntegrationSpec extends Specification {

    static final CONTROLLER_PATH = '/clients/search/findByUserName'

    @Autowired
    ClientRepository clientRepository
    @Autowired
    MockMvc mockMvc

    @Transactional
    def 'Expected JSON response is created for a valid request'() {
        given:
        def wishes = new Wishlist(products: [new Product(code: 'Sith Infiltrator')])
        def maul = new Client(active: true, firstName: 'Darth', lastName: 'Maul', wishes: [wishes])
        clientRepository.saveAndFlush(maul)

        when:
        def response = mockMvc.perform(get(CONTROLLER_PATH).param('userName', 'DARTH_MAUL'))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.lastName', equalTo('Maul')))
    }

    def '404 is returned for invalid userName'() {
        when:
        def response = mockMvc.perform(get(CONTROLLER_PATH).param('userName', 'FOO'))

        then:
        response.andExpect(status().isNotFound())
    }
}