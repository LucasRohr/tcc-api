package chaincode.certificate;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CertificateValidation implements Serializable {
	private Long ownerId;

	private Boolean isHeritageActive;

	private String hashCode;
}
