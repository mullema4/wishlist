package dk.cngroup.fetch.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.Description
import javax.persistence.*

@Entity
class Wishlist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @JsonBackReference
    @Description("The user holding items in this wishlist")
    @ManyToOne
    var client: Client? = null,

    @OrderColumn
    @ManyToMany(cascade = [CascadeType.PERSIST])
    @Description("A list of items added by the client")
    var products: List<Product> = emptyList()
)

interface WishlistRepository : JpaRepository<Wishlist?, Long?>