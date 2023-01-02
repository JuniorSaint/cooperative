package br.com.cooperative.models.entities;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.models.enums.GenderTypeEnum;
import br.com.cooperative.models.enums.MaritalStatusEnum;
import br.com.cooperative.models.enums.PersonTypeEnum;
import br.com.cooperative.models.enums.PropertyRegimeEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "members")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member extends RepresentationModel<Member> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private UsefulMethods usefulMethods;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private PersonTypeEnum personType;
    @NotBlank(message = "Cpf/Cnpj is a mandatory field")
    @Column(unique = true)
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
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "member_agencyBank",
            joinColumns = @JoinColumn(name = "agencyBank_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<AgencyBank> agenciesBanks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parameter_id")
    private Parameter parameter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name="cooperative_id")
    private Cooperative cooperative;


    public void setCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null) {
            this.cpfCnpj = cpfCnpj;
        } else {
            this.cpfCnpj = usefulMethods.justNumberAllowed(cpfCnpj);
        }
    }
}
