package dk.cngroup.wishlist.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderColumn
import org.hibernate.annotations.Formula
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@SQLRestriction("active = true") // all SELECT statements will be enhanced by where condition; cannot be inherited
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
    fun getByUserName(userName: String): Client?

    @EntityGraph(attributePaths = ["wishes"])
    fun findByUserName(userName: String): Client?

    @EntityGraph(attributePaths = ["wishes.products"])
    fun findClientByUserName(userName: String): Client
}
