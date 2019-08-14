package dk.cngroup.fetch;

import com.fasterxml.jackson.core.JsonProcessingException;
import dk.cngroup.fetch.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
	public void testFetchOrdersAndProducts() throws JsonProcessingException {
		System.out.println(clientRepository.findClientByUsername("Vader"));
	}

}