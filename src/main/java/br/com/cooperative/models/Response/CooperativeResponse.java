package br.com.cooperative.models.Response;

import br.com.cooperative.models.enums.CooperativeTypeEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"id", "name", "cnpj", "cooperativeType", "address", "contact", "agencyBanks", "matrix"})
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