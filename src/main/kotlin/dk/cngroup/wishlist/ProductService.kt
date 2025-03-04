package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.ProductRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional(propagation = REQUIRES_NEW)
    fun processProduct(product: Product) {
        product.code += " processed"
        productRepository.save(product)

        if(product.id == 2L) throw RuntimeException("Shit happens")

        logger.info { "Processed product ${product.code}" }
    }
}

private val logger = KotlinLogging.logger {}