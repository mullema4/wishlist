package dk.cngroup.wishlist

import dk.cngroup.wishlist.entity.ProductRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionalService(
    private val productService: ProductService,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun processProducts() {
        val allProducts = productRepository.findAll()
        allProducts.forEach {
            try {
                productService.processProduct(it!!) // inner transaction
            } catch (e: Exception) {
                logger.error(e) { "Error processing product ${it?.code}" }
            }
        }
        logger.info { "Finished product loop" }
        allProducts.forEach { logger.info { "Product ${it?.code}" } }
    }


}

private val logger = KotlinLogging.logger {}