package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.ClientRepository
import dk.cngroup.wishlist.entity.ProductRepository
import dk.cngroup.wishlist.entity.WishlistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
@Profile('test')
@Transactional
abstract class SpringIntegrationSpec extends Specification {
    @Autowired
    WebApplicationContext webApplicationContext
    MockMvc mockMvc
    @Autowired
    ClientRepository clientRepository
    @Autowired
    ProductRepository productRepository
    @Autowired
    WishlistRepository wishlistRepository

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }
}
