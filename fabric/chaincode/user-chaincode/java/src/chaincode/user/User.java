package chaincode.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class User implements Serializable {
	private Long userId;

	private String cpf;

	private Long birthday;

	private Long createdAt;
}
