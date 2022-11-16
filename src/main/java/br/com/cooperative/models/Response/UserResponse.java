package br.com.cooperative.models.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String userName;
    private String email;
    private Boolean active;
    private String cpf;
    private Set<RoleResponse> roles;
    private CooperativeResponse cooperative;
}