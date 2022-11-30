package br.com.cooperative.models.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "name", "phone", "email"})
public class ContactResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String phone;
    private String email;
}