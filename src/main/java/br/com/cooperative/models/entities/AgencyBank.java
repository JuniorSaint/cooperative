package br.com.cooperative.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "agenciesBanks")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AgencyBank implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String agency;
    @Column(unique = true)
    private String cnpj;
    @Column(unique = true)
    private String count;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name="cooperative_id", nullable=false)
    private Cooperative cooperative;

    public void setCnpj(String cnpj) {
        if (cnpj == null) {
            this.cnpj = cnpj;
        } else {
            this.cnpj = cnpj.replaceAll("\\D", "");
        }
    }
}