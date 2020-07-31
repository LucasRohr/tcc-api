package chaincode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CertificateValidation implements Serializable {
	private Long ownerId;

	private List<Long> heirsIds;

	private boolean isHeritageActive;

	private List<String> keyRing;
}
