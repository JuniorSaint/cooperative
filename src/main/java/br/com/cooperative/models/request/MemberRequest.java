package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.Address;
import br.com.cooperative.models.entities.Contact;
import br.com.cooperative.models.entities.Parameter;
import br.com.cooperative.models.enums.GenderTypeEnum;
import br.com.cooperative.models.enums.MaritalStatusEnum;
import br.com.cooperative.models.enums.PersonTypeEnum;
import br.com.cooperative.models.enums.PropertyRegimeEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MemberRequest  implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private PersonTypeEnum personType;
    private String cpfCnpj;
    private GenderTypeEnum gender;
    private LocalDate birthday;
    private String document;
    private String issuingAgency;
    private String nacionality;
    private MaritalStatusEnum maritalStatus;
    private PropertyRegimeEnum propertyRegime;
    private String profission;
    private String father;
    private String mother;
    private Set<AgencyBankRequest> agencyBankRequestSet;
    private Parameter parameter;
    private Address address;
    private Contact contact;
    private CooperativeRequest cooperative;
}
