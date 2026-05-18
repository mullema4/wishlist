package dk.cngroup.wishlist;

import dk.cngroup.wishlist.entity.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FetchTest {

    @Autowired
    ClientRepository clientRepository;

    //check log to see the difference in SQL executed by Hibernate
    @Test
    public void testDefaultBehavior() {
        assertNotNull(clientRepository.getByUserName("DARTH_VADER"));
    }

    @Test
    public void testFetchOrders() {
        assertNotNull(clientRepository.findByUserName("DARTH_VADER"));
    }

    @Test
    public void testFetchOrdersAndProducts() {
        assertNotNull(clientRepository.findClientByUserName("DARTH_VADER"));
    }

}
