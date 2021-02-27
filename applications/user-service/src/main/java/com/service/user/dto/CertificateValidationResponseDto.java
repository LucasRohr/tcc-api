package com.service.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateValidationResponseDto {

    @JsonProperty("codigo_hash_ok")
    private Long hashCodeValidation;

    @JsonProperty("origem_certidao")
    private String certificateOrigin;

    @JsonProperty("data_emissao")
    private String emissionDate;

    @JsonProperty("hora_emissao")
    private String emissionHour;

    @JsonProperty("data_assinatura")
    private String signatureDate;

    @JsonProperty("hora_assinatura")
    private String signatureHour;

    @JsonProperty("tipo_registro")
    private String registerType;

    @JsonProperty("nome_cartorio")
    private String registryName;

    @JsonProperty("endereco")
    private String adress;

    @JsonProperty("estado")
    private String state;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("usuario_assinante_certidao")
    private String certificateSubscriber;

    @JsonProperty("cargo_usuario")
    private String certificateSubscriberJob;

    @JsonProperty("num_dias_emissao")
    private String emissionElapsedDays;

}
