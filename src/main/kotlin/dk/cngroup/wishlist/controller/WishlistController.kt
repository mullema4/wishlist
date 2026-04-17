package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.service.WishlistService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Wishlists", description = "CRUD operations for wishlists")
class WishlistController(private val service: WishlistService) {

    @GetMapping("/wishlists")
    @Operation(summary = "List wishlists")
    fun getWishlists(): List<WishlistResponse> =
        service.getWishlists()

    @GetMapping("/wishlists/{id}")
    @Operation(summary = "Get a wishlist by id")
    fun getWishlist(
        @Parameter(description = "Wishlist identifier", example = "1")
        @PathVariable id: Long
    ): WishlistResponse =
        service.getWishlist(id)

    @PostMapping("/wishlists")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a wishlist")
    fun createWishlist(@Valid @RequestBody request: WishlistRequest): WishlistResponse =
        service.createWishlist(request)

    @PutMapping("/wishlists/{id}")
    @Operation(summary = "Replace a wishlist")
    fun replaceWishlist(
        @Parameter(description = "Wishlist identifier", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody request: WishlistRequest
    ): WishlistResponse =
        service.replaceWishlist(id, request)

    @PatchMapping("/wishlists/{id}")
    @Operation(summary = "Partially update a wishlist")
    fun updateWishlist(
        @Parameter(description = "Wishlist identifier", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody request: WishlistPatchRequest
    ): WishlistResponse =
        service.updateWishlist(id, request)

    @DeleteMapping("/wishlists/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a wishlist")
    fun deleteWishlist(
        @Parameter(description = "Wishlist identifier", example = "1")
        @PathVariable id: Long
    ) {
        service.deleteWishlist(id)
    }
}
