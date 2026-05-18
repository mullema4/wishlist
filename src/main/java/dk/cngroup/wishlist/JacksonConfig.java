package dk.cngroup.wishlist;

import org.openapitools.jackson.nullable.JsonNullableJackson3Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.JacksonModule;

@Configuration
public class JacksonConfig {

    @Bean
    public JacksonModule jsonNullableModule() {
        return new JsonNullableJackson3Module();
    }
}
