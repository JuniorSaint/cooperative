package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AccountCredentialRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
}
