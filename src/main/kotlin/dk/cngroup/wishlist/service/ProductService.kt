package dk.cngroup.wishlist.service

import dk.cngroup.wishlist.controller.*
import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val repository: ProductRepository) {

    @Transactional(readOnly = true)
    fun getProducts(): List<ProductResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun getProduct(id: Long): ProductResponse =
        repository.findByIdOrNull(id)?.toResponse() ?: throw resourceNotFound("product", id)

    @Transactional
    fun createProduct(request: ProductRequest): ProductResponse =
        repository.saveAndFlush(Product(code = request.code)).toResponse()

    @Transactional
    fun replaceProduct(id: Long, request: ProductRequest): ProductResponse {
        val product = repository.findByIdOrNull(id) ?: return repository.saveAndFlush(
            Product(code = request.code)
        ).toResponse()
        product.code = request.code
        return repository.saveAndFlush(product).toResponse()
    }

    @Transactional
    fun updateProduct(id: Long, request: ProductPatchRequest): ProductResponse {
        val product = repository.findByIdOrNull(id) ?: throw resourceNotFound("product", id)
        request.code.ifDefined { product.code = it }
        return repository.saveAndFlush(product).toResponse()
    }

    @Transactional
    fun deleteProduct(id: Long) = repository.deleteById(id)
}
