package br.com.cooperative.models.request;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest {
    private Long id;
    private String password;
}