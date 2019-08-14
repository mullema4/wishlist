package dk.cngroup.fetch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(
		name = "I-want-it-all",
		attributeNodes = {
				@NamedAttributeNode("wishes"),
				@NamedAttributeNode(value = "wishes", subgraph = "wishes-subgraph"),
		},
		subgraphs = {
				@NamedSubgraph(
						name = "wishes-subgraph",
						attributeNodes = {
								@NamedAttributeNode("products")
						}
				)
		}
)
@Entity
@Getter
@Setter
@ToString
public class Client {
	@Id
	private Long id;
	private String username;
	@OneToMany(mappedBy = "client")
	@OrderColumn
	private List<Wishlist> wishes;
}