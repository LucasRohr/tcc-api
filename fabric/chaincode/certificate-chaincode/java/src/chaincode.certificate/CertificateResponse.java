package chaincode.certificate;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponse implements Serializable {

    /*
        "codigo_hash_ok": "0",
        "origem_certidao": null,
        "data_emissao": null,
        "hora_emissao": null,
        "data_assinatura": null,
        "hora_assinatura": null,
        "tipo_registro": null,
        "nome_cartorio": null,
        "endereco": null,
        "estado": null,
        "cidade": null,
        "cep": null,
        "usuario_assinante_certidao": null,
        "cargo_usuario": null,
        "num_dias_emissao": null
     */

    @SerializedName("codigo_hash_ok")
    private short codigoHashOk;

}
