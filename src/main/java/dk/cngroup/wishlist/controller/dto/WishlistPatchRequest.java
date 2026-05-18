package dk.cngroup.wishlist.controller.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Schema(description = "Payload used to partially update a wishlist")
@Getter
@Setter
public class WishlistPatchRequest {
    @Schema(description = "Identifier of the owning client", example = "1")
    private JsonNullable<Long> clientId = JsonNullable.undefined();

    @ArraySchema(schema = @Schema(description = "Identifiers of products in the wishlist", example = "1"))
    @NotNull(message = "must not be null")
    private JsonNullable<List<Long>> productIds = JsonNullable.undefined();
}
