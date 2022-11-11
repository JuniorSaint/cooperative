package br.com.cooperative.models.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String email;
    private Boolean active;
    private String cpf;
    private List<PermissionResponse> permissions;
    private CooperativeResponse cooperative;
}