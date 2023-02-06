package dk.cngroup.wishlist.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Where
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RestResource
import jakarta.persistence.*

@Entity
@Where(clause = "active = true")  // all SELECT statements will be enhanced by where condition; cannot be inherited
class Client(
    var active: Boolean = true,
    var firstName: String,
    var lastName: String,
    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL])
    @OrderColumn
    var wishes: MutableList<Wishlist> = mutableListOf()
) : AuditableEntity() {
    @Formula("upper(concat(first_name, '_', last_name))")
    val userName: String? = null

    fun addWishlist(wishlist: Wishlist) {
        wishes += wishlist
        wishlist.client = this
    }
}

interface ClientRepository : JpaRepository<Client, Long> {
    @RestResource(exported = false)
    fun getByUserName(userName: String): Client?

    @RestResource(exported = false)
    @EntityGraph(attributePaths = ["wishes"])
    fun findByUserName(userName: String): Client?

    @EntityGraph(attributePaths = ["wishes.products"])
    fun findClientByUserName(userName: String): Client
}