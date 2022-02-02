package dk.cngroup.fetch

import dk.cngroup.fetch.entity.Client
import dk.cngroup.fetch.entity.ClientRepository
import dk.cngroup.fetch.entity.Product
import dk.cngroup.fetch.entity.Wishlist
import lombok.RequiredArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class DatabaseInitializer(private val clientRepository: ClientRepository) : CommandLineRunner {
    override fun run(vararg args: String) {
        val tieFighter = Product(code = "TIE Fighter")
        val deathStar = Product(code = "Death Star")
        val starDestroyer = Product(code = "Star Destroyer")
        val wishlist = Wishlist(products = listOf(tieFighter, deathStar, starDestroyer))
        val vader = Client(firstName = "Darth", lastName = "Vader")
        vader.addWishlist(wishlist)
        clientRepository.save(vader)
    }
}