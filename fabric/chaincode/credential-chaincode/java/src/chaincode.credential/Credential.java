package chaincode.credential;

import lombok.*;

import java.io.Serializable;

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

	private Long credentialOwnerId;

	private String heirsIds;

	private Boolean isActive;

	private Long createdAt;
}
