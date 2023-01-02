package br.com.cooperative.models.entities;

import br.com.cooperative.configs.UsefulMethods;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private UsefulMethods usefulMethods;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String phone;
    private String email;

    public void setPhone(String phone) {
        if(phone == null){
            this.phone = phone;
        }else{
            this.phone = usefulMethods.justNumberAllowed(phone);
        }
    }
}