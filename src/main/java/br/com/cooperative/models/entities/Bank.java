package br.com.cooperative.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "banks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bank extends RepresentationModel<Bank> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String code;
    private String ispb;
    private String cnpj;
    private String nameBank;
    private String url;

    @OneToMany(mappedBy = "bank")
    private Set<AgencyBank> agencyBanks;
}