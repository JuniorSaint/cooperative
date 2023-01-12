package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String role;
}
