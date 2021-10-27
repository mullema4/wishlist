package dk.cngroup.fetch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Product extends AuditableEntity {
    @Id
    Long id;

    @NotNull
    @Length(min = 3, max = 30)
    @Description("Unique name of the item")
    String code;

    @PrePersist
    void preInsert() {
        if (isBlank(code)) code = "DummyCode";
    }
}