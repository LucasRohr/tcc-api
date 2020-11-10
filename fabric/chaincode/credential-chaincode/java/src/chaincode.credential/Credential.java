package chaincode.credential;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Credential implements Serializable {
	private Long credentialId;

	private String name;

	private String description;

	private String link;

	private String login;

	private String password;

	private Long ownerId;

	private List<Long> heirsIds;

	private Boolean isActive;

	private Long createdAt;
}
