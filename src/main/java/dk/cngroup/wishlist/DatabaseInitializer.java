package dk.cngroup.wishlist;

import dk.cngroup.wishlist.entity.Client;
import dk.cngroup.wishlist.entity.Product;
import dk.cngroup.wishlist.entity.Wishlist;
import dk.cngroup.wishlist.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) {
        Product tieFighter = new Product("TIE Fighter");
        Product deathStar = new Product("Death Star");
        Product starDestroyer = new Product("Star Destroyer");

        Wishlist wishlist = new Wishlist(List.of(tieFighter, deathStar, starDestroyer));

        Client vader = new Client("Darth", "Vader");
        vader.addWishlist(wishlist);
        clientRepository.save(vader);
    }
}