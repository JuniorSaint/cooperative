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
@JsonPropertyOrder({"id", "agency", "cnpj", "count", "active"})
public class AgencyBankResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String agency;
    private String cnpj;
    private String count;
    private boolean active;
    private BankResponse bank;
    private ContactResponse contact;
    private AddressResponse address;
    private CooperativeResponse cooperative;
}