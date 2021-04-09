package chaincode.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class File implements Serializable {
    private Long fileId;

    private String symmetricKey;

    private Long createdAt;

    private Long fileOwnerId;

    private Long createdAt;
}
