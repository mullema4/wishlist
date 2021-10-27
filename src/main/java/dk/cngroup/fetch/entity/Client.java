package dk.cngroup.fetch.entity;

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
//all SELECT statements will be enhanced by given where condition; cannot be inherited from parent class
@Where(clause = "active = true")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private Boolean active;

    @Formula("upper(concat(first_name, '_', last_name))")
    private String userName;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @OrderColumn
    private List<Wishlist> wishes = new ArrayList<>();

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