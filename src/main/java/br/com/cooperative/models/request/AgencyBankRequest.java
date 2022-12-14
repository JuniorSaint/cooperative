package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AgencyBankRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String agency;
    private String count;
    private String cnpj;
    private boolean active;
    private BankRequest bank;
    private ContactRequest contact;
    private AddressRequest address;
    private CooperativeRequest cooperative;
}