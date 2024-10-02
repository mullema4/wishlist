package dk.cngroup.fetch;

import dk.cngroup.fetch.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FetchTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testDefaultBehavior() {
        clientRepository.getByUsername("Vader");
    }

    @Test
    public void testFetchOrders() {
        clientRepository.findByUsername("Vader");
    }

    @Test
    public void testFetchOrdersAndProducts() {
        System.out.println(clientRepository.findClientByUsername("Vader"));
    }

}