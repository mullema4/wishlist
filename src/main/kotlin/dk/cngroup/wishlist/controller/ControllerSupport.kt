package dk.cngroup.wishlist.controller

import org.openapitools.jackson.nullable.JsonNullable
import org.springframework.dao.EmptyResultDataAccessException

internal fun resourceNotFound(resource: String, id: Long): EmptyResultDataAccessException =
    EmptyResultDataAccessException("No $resource found for id $id", 1)

internal inline fun <T> JsonNullable<T>.ifDefined(block: (T) -> Unit) {
    if (isPresent) {
        block(get())
    }
}
