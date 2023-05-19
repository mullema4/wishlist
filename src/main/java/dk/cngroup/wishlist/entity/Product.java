package dk.cngroup.wishlist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends AuditableEntity {
    @Description("Unique name of the item")
    @NotNull
    @Length(min = 3, max = 30)
    String code;

    public Product(String code) {
        this.code = code;
    }
}