package dk.cngroup.fetch.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.List;

@Entity
//all SELECT statements will be enhanced by given where condition; cannot be inherited from parent class
@Where(clause = "active = true")
@Getter
@Setter
@ToString(callSuper = true)
public class Client {
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private Boolean active;

    @Formula("upper(concat(first_name, '_', last_name))")
    private String userName;

    @JsonManagedReference
    @OneToMany(mappedBy = "client")
    @OrderColumn
    private List<Wishlist> wishes;
}