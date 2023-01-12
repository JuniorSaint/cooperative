package br.com.cooperative.models.Response;


import br.com.cooperative.models.entities.Role;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "userName", "email", "cpf", "birthday", "age", "active"})
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String userName;
    private String email;
    private Boolean active;
    private String cpf;
    private LocalDate birthday;
    private Set<Role> roles;
    private CooperativeResponse cooperative;
    private Integer age;
    private String imageFileName;


    private Integer calculateAge(LocalDate birthday) {
        if (birthday != null) {
            return Period.between(birthday, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public Integer getAge() {
       return  calculateAge(getBirthday());
    }
}