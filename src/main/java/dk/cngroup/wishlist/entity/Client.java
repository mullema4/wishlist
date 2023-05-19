package dk.cngroup.wishlist.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "active = true") // all SELECT statements will be enhanced by given where condition; cannot be inherited from parent class

@Getter
@Setter
@NoArgsConstructor
public class Client extends AuditableEntity {
    private String firstName;

    private String lastName;

    private Boolean active;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @OrderColumn
    private List<Wishlist> wishes = new ArrayList<>();

    @Formula("upper(concat(first_name, '_', last_name))")
    private String userName;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = true;
    }

    public void addWishlist(Wishlist wishlist) {
        wishes.add(wishlist);
        wishlist.setClient(this);
    }
}