package dk.cngroup.wishlist.controller;

import dk.cngroup.wishlist.controller.dto.WishlistPatchRequest;
import dk.cngroup.wishlist.controller.dto.WishlistRequest;
import dk.cngroup.wishlist.controller.dto.WishlistResponse;
import dk.cngroup.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Tag(name = "Wishlists", description = "CRUD operations for wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService service;

    @GetMapping("/wishlists")
    @Operation(summary = "List wishlists")
    public List<WishlistResponse> getWishlists() {
        return service.getWishlists();
    }

    @GetMapping("/wishlists/{id}")
    @Operation(summary = "Get a wishlist by id")
    public WishlistResponse getWishlist(
            @Parameter(description = "Wishlist identifier", example = "1")
            @PathVariable Long id) {
        return service.getWishlist(id);
    }

    @PostMapping("/wishlists")
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a wishlist")
    public WishlistResponse createWishlist(@Valid @RequestBody WishlistRequest request) {
        return service.createWishlist(request);
    }

    @PutMapping("/wishlists/{id}")
    @Operation(summary = "Replace a wishlist")
    public WishlistResponse replaceWishlist(
            @Parameter(description = "Wishlist identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody WishlistRequest request) {
        return service.replaceWishlist(id, request);
    }

    @PatchMapping("/wishlists/{id}")
    @Operation(summary = "Partially update a wishlist")
    public WishlistResponse updateWishlist(
            @Parameter(description = "Wishlist identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody WishlistPatchRequest request) {
        return service.updateWishlist(id, request);
    }

    @DeleteMapping("/wishlists/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a wishlist")
    public void deleteWishlist(
            @Parameter(description = "Wishlist identifier", example = "1")
            @PathVariable Long id) {
        service.deleteWishlist(id);
    }
}
