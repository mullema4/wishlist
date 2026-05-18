package dk.cngroup.wishlist.controller.dto;

import dk.cngroup.wishlist.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Product returned by the API")
public record ProductResponse(
        @Schema(description = "Unique product identifier", example = "1")
        Long id,
        @Schema(description = "Human-readable product code", example = "TIE Fighter")
        String code,
        @Schema(description = "Timestamp when the product was created")
        LocalDateTime created,
        @Schema(description = "Timestamp when the product was last updated")
        LocalDateTime updated,
        @Schema(description = "User that created the product", example = "system")
        String createdBy
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getCreated(),
                product.getUpdated(),
                product.getCreatedBy()
        );
    }
}
