package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.*;
import br.com.cooperative.models.entities.Parameter;
import br.com.cooperative.models.enums.GenderTypeEnum;
import br.com.cooperative.models.enums.MaritalStatusEnum;
import br.com.cooperative.models.enums.PersonTypeEnum;
import br.com.cooperative.models.enums.PropertyRegimeEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
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
    private Set<OnlyIdRequest> agenciesBanks;
    private Parameter parameter;
    private Address address;
    private Contact contact;
    private OnlyIdRequest cooperative;
}
