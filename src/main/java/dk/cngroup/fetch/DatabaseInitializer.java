package dk.cngroup.fetch;

import dk.cngroup.fetch.entity.Product;
import dk.cngroup.fetch.repository.ClientRepository;
import dk.cngroup.fetch.repository.ProductRepository;
import dk.cngroup.fetch.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    @Override
    public void run(String... args) {
        Product tieFighter = new Product();
//        tieFighter.setCode();
//        productRepository.saveAll()
    }
}
