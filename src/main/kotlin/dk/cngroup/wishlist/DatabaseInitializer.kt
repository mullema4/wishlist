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
        val tieFighter = Product(code = "TIE Fighter", description = "Standard Imperial starfighter")
        val deathStar = Product(code = "Death Star", description = "Armed space station")
        val starDestroyer = Product(code = "Star Destroyer", description = "Imperial capital ship")
        val imperialShuttle = Product(code = "Imperial Shuttle", description = "Transport ship")
        val sithInterceptor = Product(code = "Sith Interceptor")//, description = "Sith starfighter")
        val wishlist = Wishlist(products = arrayListOf(tieFighter, deathStar, starDestroyer, imperialShuttle, sithInterceptor))
        val vader = Client(firstName = "Darth", lastName = "Vader")

        vader.addWishlist(wishlist)
        clientRepository.save(vader)
    }
}