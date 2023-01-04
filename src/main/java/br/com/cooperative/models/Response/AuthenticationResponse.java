package br.com.cooperative.models.Response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;

}
