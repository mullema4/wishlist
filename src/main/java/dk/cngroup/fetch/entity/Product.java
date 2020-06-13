package dk.cngroup.fetch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
public class Product {
	@Id
	Long id;

	@NotNull
	@Length(min = 3, max = 30)
	@Description("Serial number of the item")
	String code;
}