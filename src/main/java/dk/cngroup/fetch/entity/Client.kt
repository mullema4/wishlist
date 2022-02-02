package dk.cngroup.fetch.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Where
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RestResource
import javax.persistence.*

@Entity //all SELECT statements will be enhanced by given where condition; cannot be inherited from parent class
@Where(clause = "active = true")
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var active: Boolean = true,
    val firstName: String,
    val lastName: String,
    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL])
    @OrderColumn
    val wishes: MutableList<Wishlist> = mutableListOf()
) {
    @Formula("upper(concat(first_name, '_', last_name))")
    val userName: String? = null

    fun addWishlist(wishlist: Wishlist) {
        wishes += wishlist
        wishlist.client = this
    }
}

interface ClientRepository : CrudRepository<Client?, Long?> {
    @RestResource(exported = false)
    fun getByUserName(userName: String?): Client?

    @RestResource(exported = false)
    @EntityGraph(attributePaths = ["wishes"])
    fun findByUserName(userName: String?): Client?

    @EntityGraph(attributePaths = ["wishes.products"])
    fun findClientByUserName(userName: String?): Client?
}