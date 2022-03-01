package dk.cngroup.fetch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void noAuthenticationTest() throws Exception {
        mockMvc.perform(get("/clients/client-management/DARTH_VADER")).andExpect(status().isOk());
    }
}
