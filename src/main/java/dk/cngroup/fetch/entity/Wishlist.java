package dk.cngroup.fetch.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "client")
public class Wishlist {
    @Id
    Long id;

    @JsonBackReference
    @Description("The user holding items in this wishlist")
    @ManyToOne
    Client client;

    @Description("A list of items added by the client")
    @ManyToMany
    @OrderColumn
    List<Product> products;
}