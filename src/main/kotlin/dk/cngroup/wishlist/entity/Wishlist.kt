package dk.cngroup.wishlist.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
class Wishlist(
    @JsonBackReference
    @ManyToOne
    var client: Client? = null,

    @OrderColumn
    @ManyToMany(cascade = [CascadeType.PERSIST])
    var products: MutableList<Product> = arrayListOf()
) : AuditableEntity()

interface WishlistRepository : JpaRepository<Wishlist, Long>
