package dk.cngroup.wishlist.controller.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "Payload used to create or fully replace a wishlist")
@Getter
@Setter
public class WishlistRequest {
    @Schema(description = "Identifier of the owning client", example = "1")
    @NotNull
    private Long clientId;

    @ArraySchema(schema = @Schema(description = "Identifiers of products in the wishlist", example = "1"))
    @NotNull
    private List<Long> productIds = List.of();
}
