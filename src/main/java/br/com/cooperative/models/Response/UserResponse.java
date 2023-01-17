package br.com.cooperative.models.Response;


import br.com.cooperative.models.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
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
    private LocalDate birthday;
    private Boolean active;
    private String cpf;
    private String imageFileName;
    private RoleEnum role;
    private CooperativeResponse cooperative;


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