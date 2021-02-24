package com.service.user.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateValidationResponseDto {

    @SerializedName("codigo_hash_ok")
    private Long hashCodeValidation;

    @SerializedName("origem_certidao")
    private String certificateOrigin;

    @SerializedName("data_emissao")
    private String emissionDate;

    @SerializedName("hora_emissao")
    private String emissionHour;

    @SerializedName("data_assinatura")
    private String signatureDate;

    @SerializedName("hora_assinatura")
    private String signatureHour;

    @SerializedName("tipo_registro")
    private String registerType;

    @SerializedName("nome_cartorio")
    private String registryName;

    @SerializedName("endereco")
    private String adress;

    @SerializedName("estado")
    private String state;

    @SerializedName("cidade")
    private String city;

    @SerializedName("cep")
    private String cep;

    @SerializedName("usuario_assinante_certidao")
    private String certificateSubscriber;

    @SerializedName("cargo_usuario")
    private String certificateSubscriberJob;

    @SerializedName("num_dias_emissao")
    private String emissionElapsedDays;

}
