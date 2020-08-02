package chaincode;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CertificateValidation implements Serializable {
	private Long ownerId;

	private List<Long> heirsIds;

	private boolean isHeritageActive;

	private List<String> keyRing;
}
