package chaincode.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long accountId;
    
    private String privateKey;
    
    private String publicKey;

    private String accountType;
}
