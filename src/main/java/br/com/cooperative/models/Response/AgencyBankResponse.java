package br.com.cooperative.models.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyBankResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String agency;
    private String cnpj;
    private String count;
    private boolean active;
    private BankResponse bank;
    private ContactResponse contact;
    private AddressResponse address;
    private CooperativeResponse cooperative;
}