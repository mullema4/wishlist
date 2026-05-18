package dk.cngroup.wishlist.controller.dto;

import dk.cngroup.wishlist.entity.Client;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Schema(description = "Client returned by the API")
public record ClientResponse(
        @Schema(description = "Unique client identifier", example = "1")
        Long id,
        @Schema(description = "Whether the client is active", example = "true")
        boolean active,
        @Schema(description = "Client first name", example = "Darth")
        String firstName,
        @Schema(description = "Client last name", example = "Vader")
        String lastName,
        @Schema(description = "Derived user name", example = "DARTH_VADER")
        String userName,
        @ArraySchema(schema = @Schema(
                implementation = WishlistResponse.class,
                description = "Wishlists owned by the client"
        ))
        List<WishlistResponse> wishes,
        @Schema(description = "Timestamp when the client was created")
        LocalDateTime created,
        @Schema(description = "Timestamp when the client was last updated")
        LocalDateTime updated,
        @Schema(description = "User that created the client", example = "system")
        String createdBy
) {
    public static ClientResponse from(Client client) {
        List<WishlistResponse> wishes = client.getWishes() == null
                ? List.of()
                : client.getWishes().stream().map(WishlistResponse::from).toList();
        return new ClientResponse(
                client.getId(),
                Boolean.TRUE.equals(client.getActive()),
                client.getFirstName(),
                client.getLastName(),
                "%s_%s".formatted(client.getFirstName(), client.getLastName()).toUpperCase(Locale.ROOT),
                wishes,
                client.getCreated(),
                client.getUpdated(),
                client.getCreatedBy()
        );
    }
}
