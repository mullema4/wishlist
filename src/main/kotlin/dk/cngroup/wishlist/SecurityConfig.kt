package dk.cngroup.wishlist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import java.util.*

@Configuration
class SecurityConfig : AuditorAware<String> {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun userDetailsService(
        passwordEncoder: PasswordEncoder
    ): InMemoryUserDetailsManager {
        val user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            httpBasic { }
            authorizeHttpRequests {
                authorize("/actuator/health/**", permitAll)
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }

    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        return Optional.ofNullable(authentication?.name)
    }
}
