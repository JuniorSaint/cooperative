package br.com.cooperative.models.entities;


import br.com.cooperative.configs.UsefulMethods;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private UsefulMethods usefulMethods;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String street;
    private String number;
    private String district;
    private String city;
    private String uf;
    private String complemente;
    private String zipCode;
    public void setZipCode(String zipCode) {
        if (zipCode == null) {
            this.zipCode = zipCode;
        } else {
            this.zipCode = usefulMethods.justNumberAllowed(zipCode);
        }
    }
}
