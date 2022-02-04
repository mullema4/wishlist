package dk.cngroup.fetch.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.Description
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Description("Unique name of the item")
    @field:NotNull
    val code: String
) : AuditableEntity()

interface ProductRepository : JpaRepository<Product?, Long?>