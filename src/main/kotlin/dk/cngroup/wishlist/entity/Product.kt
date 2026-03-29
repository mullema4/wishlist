package dk.cngroup.wishlist.entity

import jakarta.persistence.Entity
import jakarta.validation.constraints.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.Description

@Entity
class Product(
    @Description("Unique name of the item")
    @field:NotNull
    var code: String
) : AuditableEntity()

interface ProductRepository : JpaRepository<Product, Long>
