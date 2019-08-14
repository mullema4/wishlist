package dk.cngroup.fetch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "client")
public class Wishlist {
	@Id
	Long id;
	@ManyToOne
	Client client;
	@ManyToMany
	@OrderColumn
	List<Product> products;
}