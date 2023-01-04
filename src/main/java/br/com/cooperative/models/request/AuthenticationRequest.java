package br.com.cooperative.models.request;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
}
