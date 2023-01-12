package br.com.cooperative.models.request;


import br.com.cooperative.models.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
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
    private Boolean active;
    private String cpf;
    private Set<Role> roles;
    private CooperativeRequest cooperative;
    private String imageFileName;
}