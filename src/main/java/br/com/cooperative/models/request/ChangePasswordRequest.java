package br.com.cooperative.models.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest {
    private UUID id;
    private String password;
}