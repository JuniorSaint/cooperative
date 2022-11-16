package br.com.cooperative.models.Response;

import br.com.cooperative.models.entities.Address;
import br.com.cooperative.models.entities.Contact;
import br.com.cooperative.models.enums.CooperativeTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CooperativeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private CooperativeTypeEnum cooperativeType;
    private String cnpj;
    private CooperativeResponse matrix;
    private AddressResponse address;
    private ContactResponse contact;
    private Set<AgencyBankResponse> agencyBanks;
}