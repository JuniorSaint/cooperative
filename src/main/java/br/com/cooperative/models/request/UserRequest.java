package br.com.cooperative.models.request;


import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.enums.RoleEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest implements  Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String userName;
    private String email;
    private String password;
    private LocalDate birthday;
    private Boolean active;
    private String cpf;
    private String imageFileName;
    private RoleEnum role;
    private Cooperative cooperative;
}