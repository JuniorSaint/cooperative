package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.Cooperative;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TemplateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String body;
    private OnlyIdRequest cooperative;
}