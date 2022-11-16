package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String phone;
    private String email;
}