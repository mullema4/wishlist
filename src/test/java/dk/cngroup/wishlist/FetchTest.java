package dk.cngroup.wishlist;

import dk.cngroup.wishlist.entity.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FetchTest {

    // TODO -> Spock
    @Autowired
    ClientRepository clientRepository;

    // TODO test log content https://stackoverflow.com/a/51812144/2431410
    // check log to see the difference in SQL executed by Hibernate
    @Test
    public void testDefaultBehavior() {
        clientRepository.getByUserName("DARTH_VADER");
    }

    @Test
    public void testFetchOrders() {
        clientRepository.findByUserName("DARTH_VADER");
    }

    @Test
    public void testFetchOrdersAndProducts() {
        clientRepository.findClientByUserName("DARTH_VADER");
    }

}