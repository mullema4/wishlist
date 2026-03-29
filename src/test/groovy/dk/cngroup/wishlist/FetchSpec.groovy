package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FetchSpec extends Specification {

    @Autowired
    ClientRepository clientRepository

    // TODO test log content https://stackoverflow.com/a/51812144/2431410
    // check log to see the difference in SQL executed by Hibernate
    def 'default repository lookup loads Darth Vader'() {
        expect:
        clientRepository.getByUserName('DARTH_VADER') != null
    }

    def 'entity graph lookup fetches orders for Darth Vader'() {
        expect:
        clientRepository.findByUserName('DARTH_VADER') != null
    }

    def 'nested entity graph lookup fetches orders and products for Darth Vader'() {
        expect:
        clientRepository.findClientByUserName('DARTH_VADER') != null
    }
}
