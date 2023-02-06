package dk.cngroup.wishlist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import javax.persistence.EntityManager

//TODO migrate fixes to Java branch
@SpringBootApplication
@EnableJpaAuditing
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Configuration
class CustomizedRestMvcConfiguration(val entityManager: EntityManager) : RepositoryRestConfigurer {
    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration, corsRegistry: CorsRegistry) {
        entityManager.metamodel.entities.forEach { config.exposeIdsFor(it.javaType) }
    }
}