package dk.cngroup.wishlist.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Schema(description = "Payload used to partially update a client")
@Getter
@Setter
public class ClientPatchRequest {
    @Schema(description = "Whether the client is active", example = "false")
    @NotNull(message = "must not be null")
    private JsonNullable<Boolean> active = JsonNullable.undefined();

    @Schema(description = "Client first name", example = "Anakin")
    @NotBlank(message = "must not be blank")
    private JsonNullable<String> firstName = JsonNullable.undefined();

    @Schema(description = "Client last name", example = "Skywalker")
    @NotBlank(message = "must not be blank")
    private JsonNullable<String> lastName = JsonNullable.undefined();
}
