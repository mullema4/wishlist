package dk.cngroup.wishlist.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload used to create or fully replace a product")
public record ProductRequest(
        @Schema(description = "Human-readable product code", example = "Death Star")
        @NotBlank
        String code
) {
}
