package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BankRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String code;
    private String ispb;
    private String cnpj;
    private String nameBank;
    private String url;
}