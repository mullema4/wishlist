package dk.cngroup.wishlist

import org.openapitools.jackson.nullable.JsonNullableJackson3Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.JacksonModule

@Configuration
class JacksonConfig {

    @Bean
    fun jsonNullableModule(): JacksonModule = JsonNullableJackson3Module()
}
