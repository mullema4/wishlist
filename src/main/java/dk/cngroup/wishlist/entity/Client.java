package dk.cngroup.wishlist.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SQLRestriction("active = true") // all SELECT statements will be enhanced by where condition; cannot be inherited

@Getter
@Setter
@NoArgsConstructor
public class Client extends AuditableEntity {
    private Boolean active = true;

    private String firstName;

    private String lastName;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @OrderColumn
    private List<Wishlist> wishes = new ArrayList<>();

    @Formula("upper(concat(first_name, '_', last_name))")
    private String userName;

    public Client(Boolean active, String firstName, String lastName) {
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(Boolean active, String firstName, String lastName, List<Wishlist> wishes) {
        this(active, firstName, lastName);
        this.wishes = wishes;
    }

    public Client(String firstName, String lastName) {
        this(true, firstName, lastName);
    }

    public void addWishlist(Wishlist wishlist) {
        wishes.add(wishlist);
        wishlist.setClient(this);
    }
}
