package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AgencyBankRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String agency;
    private String count;
    private String cnpj;
    private boolean active;
    private OnlyIdRequest bank;
    private ContactRequest contact;
    private AddressRequest address;
    private OnlyIdRequest cooperative;
}