package dk.cngroup.wishlist.controller

import dk.cngroup.wishlist.entity.Client
import dk.cngroup.wishlist.entity.Product
import dk.cngroup.wishlist.entity.Wishlist
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.openapitools.jackson.nullable.JsonNullable
import java.time.LocalDateTime
import java.util.*

@Schema(description = "Payload used to create or fully replace a product")
data class ProductRequest(
    @field:Schema(description = "Human-readable product code", example = "Death Star")
    @field:NotBlank
    val code: String
)

@Schema(description = "Payload used to partially update a product")
class ProductPatchRequest {
    @field:Schema(description = "Human-readable product code", example = "Executor")
    @field:NotBlank(message = "must not be blank")
    var code: JsonNullable<String> = JsonNullable.undefined()
}

@Schema(description = "Product returned by the API")
data class ProductResponse(
    @field:Schema(description = "Unique product identifier", example = "1")
    val id: Long?,
    @field:Schema(description = "Human-readable product code", example = "TIE Fighter")
    val code: String,
    @field:Schema(description = "Timestamp when the product was created")
    val created: LocalDateTime?,
    @field:Schema(description = "Timestamp when the product was last updated")
    val updated: LocalDateTime?,
    @field:Schema(description = "User that created the product", example = "system")
    val createdBy: String?
)

internal fun Product.toResponse() = ProductResponse(
    id = id,
    code = code,
    created = created,
    updated = updated,
    createdBy = createdBy
)

@Schema(description = "Payload used to create or fully replace a client")
data class ClientRequest(
    @field:Schema(description = "Whether the client is active", example = "true")
    val active: Boolean = true,
    @field:Schema(description = "Client first name", example = "Darth")
    @field:NotBlank
    val firstName: String,
    @field:Schema(description = "Client last name", example = "Vader")
    @field:NotBlank
    val lastName: String
)

@Schema(description = "Payload used to partially update a client")
class ClientPatchRequest {
    @field:Schema(description = "Whether the client is active", example = "false")
    @field:NotNull(message = "must not be null")
    var active: JsonNullable<Boolean> = JsonNullable.undefined()

    @field:Schema(description = "Client first name", example = "Anakin")
    @field:NotBlank(message = "must not be blank")
    var firstName: JsonNullable<String> = JsonNullable.undefined()

    @field:Schema(description = "Client last name", example = "Skywalker")
    @field:NotBlank(message = "must not be blank")
    var lastName: JsonNullable<String> = JsonNullable.undefined()
}

@Schema(description = "Client returned by the API")
data class ClientResponse(
    @field:Schema(description = "Unique client identifier", example = "1")
    val id: Long?,
    @field:Schema(description = "Whether the client is active", example = "true")
    val active: Boolean,
    @field:Schema(description = "Client first name", example = "Darth")
    val firstName: String,
    @field:Schema(description = "Client last name", example = "Vader")
    val lastName: String,
    @field:Schema(description = "Derived user name", example = "DARTH_VADER")
    val userName: String,
    @field:ArraySchema(
        schema = Schema(
            implementation = WishlistResponse::class,
            description = "Wishlists owned by the client"
        )
    )
    val wishes: List<WishlistResponse>,
    @field:Schema(description = "Timestamp when the client was created")
    val created: LocalDateTime?,
    @field:Schema(description = "Timestamp when the client was last updated")
    val updated: LocalDateTime?,
    @field:Schema(description = "User that created the client", example = "system")
    val createdBy: String?
)

internal fun Client.toResponse() = ClientResponse(
    id = id,
    active = active,
    firstName = firstName,
    lastName = lastName,
    userName = "${firstName}_${lastName}".uppercase(Locale.ROOT),
    wishes = wishes.map(Wishlist::toResponse),
    created = created,
    updated = updated,
    createdBy = createdBy
)

@Schema(description = "Payload used to create or fully replace a wishlist")
data class WishlistRequest(
    @field:Schema(description = "Identifier of the owning client", example = "1")
    @field:NotNull
    val clientId: Long?,
    @field:ArraySchema(schema = Schema(description = "Identifiers of products in the wishlist", example = "1"))
    val productIds: List<Long> = emptyList()
)

@Schema(description = "Payload used to partially update a wishlist")
class WishlistPatchRequest {
    @field:Schema(description = "Identifier of the owning client", example = "1")
    var clientId: JsonNullable<Long?> = JsonNullable.undefined()

    @field:ArraySchema(schema = Schema(description = "Identifiers of products in the wishlist", example = "1"))
    @field:NotNull(message = "must not be null")
    var productIds: JsonNullable<List<Long>> = JsonNullable.undefined()
}

@Schema(description = "Wishlist returned by the API")
data class WishlistResponse(
    @field:Schema(description = "Unique wishlist identifier", example = "1")
    val id: Long?,
    @field:Schema(description = "Identifier of the owning client", example = "1")
    val clientId: Long?,
    @field:ArraySchema(
        schema = Schema(
            implementation = ProductResponse::class,
            description = "Products in the wishlist"
        )
    )
    val products: List<ProductResponse>,
    @field:Schema(description = "Timestamp when the wishlist was created")
    val created: LocalDateTime?,
    @field:Schema(description = "Timestamp when the wishlist was last updated")
    val updated: LocalDateTime?,
    @field:Schema(description = "User that created the wishlist", example = "system")
    val createdBy: String?
)

internal fun Wishlist.toResponse() = WishlistResponse(
    id = id,
    clientId = client?.id,
    products = products.map(Product::toResponse),
    created = created,
    updated = updated,
    createdBy = createdBy
)
