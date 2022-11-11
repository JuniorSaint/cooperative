package br.com.cooperative.models.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse implements Serializable {

    private Long id;
    private String code;
    private String ispb;
    private String cnpj;
    private String bank;
    private String url;
}