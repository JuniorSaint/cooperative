package br.com.cooperative.models.Response;

import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Set<String> roles;
}
