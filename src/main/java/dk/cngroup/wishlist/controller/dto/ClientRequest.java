package dk.cngroup.wishlist.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Payload used to create or fully replace a client")
@Getter
@Setter
public class ClientRequest {
    @Schema(description = "Whether the client is active", example = "true")
    @NotNull
    private Boolean active = true;

    @Schema(description = "Client first name", example = "Darth")
    @NotBlank
    private String firstName;

    @Schema(description = "Client last name", example = "Vader")
    @NotBlank
    private String lastName;
}
