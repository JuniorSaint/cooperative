package br.com.cooperative.models.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "nameBank", "code", "bank", "cnpj", "ispb", "url"})
@Builder
public class BankResponse implements Serializable {

    private UUID id;
    private String nameBank;
    private String code;
    private String ispb;
    private String cnpj;
    private String bank;
    private String url;
}