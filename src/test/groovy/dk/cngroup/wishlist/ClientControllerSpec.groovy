package dk.cngroup.wishlist


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    def 'Expected JSON response is created for a valid request'() {
        when:
        def response = mockMvc.perform(get("/clients/client-management/DARTH_VADER"))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$.lastName', equalTo('Vader')))
    }

    def '404 is returned for invalid userName'() {
        when:
        def response = mockMvc.perform(get("/clients/client-management/FOO"))

        then:
        response.andExpect(status().isNotFound())
    }
}

