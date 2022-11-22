package br.com.cooperative.models.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "code", "bank", "cnpj", "ispb", "url"})
public class BankResponse implements Serializable {

    private UUID id;
    private String code;
    private String ispb;
    private String cnpj;
    private String bank;
    private String url;
}