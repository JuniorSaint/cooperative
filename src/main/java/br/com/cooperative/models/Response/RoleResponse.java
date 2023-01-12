package br.com.cooperative.models.Response;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String role;
}
