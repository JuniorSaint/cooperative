package br.com.cooperative.models.entities;

import br.com.cooperative.models.enums.CooperativeTypeEnum;
import br.com.cooperative.models.enums.PersonTypeEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "cooperatives")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cooperative implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private CooperativeTypeEnum cooperativeType;
    @NotBlank(message = "Cnpj is a mandatory field")
    @Column(unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "cooperative")
    private Set<User> users;

    @OneToMany(mappedBy = "matrix")
    private Set<Cooperative> branchs;
    @ManyToOne
    @JoinColumn(name = "cooperative_id")
    private Cooperative matrix;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @OneToMany(mappedBy="cooperative")
    private Set<Member> members;

    @ManyToMany
    @JoinTable(
            name = "member_agencyBank",
            joinColumns = @JoinColumn(name = "agencyBank_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<AgencyBank> agencyBanks;

    public void setCnpj(String cnpj) {
        if (cnpj == null) {
            this.cnpj = cnpj;
        } else {
            this.cnpj = cnpj.replaceAll("\\D", "");
        }
    }
}