package dk.cngroup.wishlist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist extends AuditableEntity {
    @JsonBackReference
    @ManyToOne
    private Client client;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @OrderColumn
    private List<Product> products = new ArrayList<>();

    public Wishlist(List<Product> products) {
        this.products = products;
    }
}
