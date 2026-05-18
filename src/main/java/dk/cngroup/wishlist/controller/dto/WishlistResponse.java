package dk.cngroup.wishlist.controller.dto;

import dk.cngroup.wishlist.entity.Wishlist;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Wishlist returned by the API")
public record WishlistResponse(
        @Schema(description = "Unique wishlist identifier", example = "1")
        Long id,
        @Schema(description = "Identifier of the owning client", example = "1")
        Long clientId,
        @ArraySchema(schema = @Schema(
                implementation = ProductResponse.class,
                description = "Products in the wishlist"
        ))
        List<ProductResponse> products,
        @Schema(description = "Timestamp when the wishlist was created")
        LocalDateTime created,
        @Schema(description = "Timestamp when the wishlist was last updated")
        LocalDateTime updated,
        @Schema(description = "User that created the wishlist", example = "system")
        String createdBy
) {
    public static WishlistResponse from(Wishlist wishlist) {
        List<ProductResponse> products = wishlist.getProducts() == null
                ? List.of()
                : wishlist.getProducts().stream().map(ProductResponse::from).toList();
        return new WishlistResponse(
                wishlist.getId(),
                wishlist.getClient() == null ? null : wishlist.getClient().getId(),
                products,
                wishlist.getCreated(),
                wishlist.getUpdated(),
                wishlist.getCreatedBy()
        );
    }
}
