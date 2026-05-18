package dk.cngroup.wishlist.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Schema(description = "Payload used to partially update a product")
@Getter
@Setter
public class ProductPatchRequest {
    @Schema(description = "Human-readable product code", example = "Executor")
    @NotBlank(message = "must not be blank")
    private JsonNullable<String> code = JsonNullable.undefined();
}
