package dk.cngroup.wishlist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.Description;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wishlist extends AuditableEntity {
    @JsonBackReference
    @Description("The user holding items in this wishlist")
    @ManyToOne
    Client client;

    @Description("A list of items added by the client")
    @ManyToMany(cascade = CascadeType.PERSIST)
    @OrderColumn
    List<Product> products;

    public Wishlist(List<Product> products) {
        this.products = products;
    }
}