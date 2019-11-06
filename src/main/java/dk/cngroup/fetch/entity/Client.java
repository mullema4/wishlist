package dk.cngroup.fetch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Client {
	@Id
	private Long id;

	@Description("Concatenated first name and last name of the user")
	private String username;

	@OneToMany(mappedBy = "client")
	@OrderColumn
	private List<Wishlist> wishes;
}