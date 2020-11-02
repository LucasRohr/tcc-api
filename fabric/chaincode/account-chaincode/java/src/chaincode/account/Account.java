package chaincode.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    private Long accountId;
    
    private String privateKey;
    
    private String publicKey;

    private String accountType;
}
