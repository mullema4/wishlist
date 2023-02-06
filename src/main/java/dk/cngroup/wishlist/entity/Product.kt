package dk.cngroup.wishlist.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.Description
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Product(
    @Description("Unique name of the item")
    @field:NotNull
    var code: String
) : AuditableEntity()

interface ProductRepository : JpaRepository<Product?, Long?>