package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.Client
import dk.cngroup.wishlist.entity.ClientRepository
import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.Wishlist
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(private val clientRepository: ClientRepository) : CommandLineRunner {
    override fun run(vararg args: String) {
        val tieFighter = Product(code = "TIE Fighter")
        val deathStar = Product(code = "Death Star")
        val starDestroyer = Product(code = "Star Destroyer")
        val wishlist = Wishlist(products = arrayListOf(tieFighter, deathStar, starDestroyer))
        val vader = Client(firstName = "Darth", lastName = "Vader")
        vader.addWishlist(wishlist)
        clientRepository.save(vader)
    }
}