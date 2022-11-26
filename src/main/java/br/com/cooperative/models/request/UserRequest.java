package br.com.cooperative.models.request;


import lombok.*;

import java.io.Serializable;
import java.util.List;
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
    private List<RoleRequest> roles;
    private OnlyIdRequest cooperative;
}