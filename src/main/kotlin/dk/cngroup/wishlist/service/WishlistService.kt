package dk.cngroup.wishlist.service

import dk.cngroup.wishlist.controller.*
import dk.cngroup.wishlist.entity.*
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WishlistService(
    private val wishlistRepository: WishlistRepository,
    private val clientRepository: ClientRepository,
    private val productRepository: ProductRepository
) {

    @Transactional(readOnly = true)
    fun getWishlists(): List<WishlistResponse> =
        wishlistRepository.findAll(Sort.by(ASC, "id")).map(Wishlist::toResponse)

    @Transactional(readOnly = true)
    fun getWishlist(id: Long): WishlistResponse =
        wishlistRepository.findByIdOrNull(id)?.toResponse() ?: throw resourceNotFound("wishlist", id)

    @Transactional
    fun createWishlist(request: WishlistRequest): WishlistResponse =
        wishlistRepository.saveAndFlush(
            Wishlist(
                client = getClient(request.clientId!!),
                products = getProducts(request.productIds).toMutableList()
            )
        ).toResponse()

    @Transactional
    fun replaceWishlist(id: Long, request: WishlistRequest): WishlistResponse {
        val wishlist = wishlistRepository.findByIdOrNull(id) ?: return wishlistRepository.saveAndFlush(
            Wishlist(
                client = getClient(request.clientId!!),
                products = getProducts(request.productIds).toMutableList()
            )
        ).toResponse()
        wishlist.client = getClient(request.clientId!!)
        wishlist.products.clear()
        wishlist.products.addAll(getProducts(request.productIds))
        return wishlistRepository.saveAndFlush(wishlist).toResponse()
    }

    @Transactional
    fun updateWishlist(id: Long, request: WishlistPatchRequest): WishlistResponse {
        val wishlist = wishlistRepository.findByIdOrNull(id) ?: throw resourceNotFound("wishlist", id)
        request.clientId.ifDefined { wishlist.client = it?.let(::getClient) }
        request.productIds.ifDefined {
            wishlist.products.clear()
            wishlist.products.addAll(getProducts(it))
        }
        return wishlistRepository.saveAndFlush(wishlist).toResponse()
    }

    @Transactional
    fun deleteWishlist(id: Long) = wishlistRepository.deleteById(id)

    private fun getClient(id: Long): Client =
        clientRepository.findByIdOrNull(id) ?: throw resourceNotFound("client", id)

    private fun getProducts(ids: List<Long>): List<Product> {
        if (ids.isEmpty()) return emptyList()

        val productsById = productRepository.findAllById(ids).associateBy { it.id }
        return ids.map { productId ->
            productsById[productId] ?: throw resourceNotFound("product", productId)
        }
    }
}
