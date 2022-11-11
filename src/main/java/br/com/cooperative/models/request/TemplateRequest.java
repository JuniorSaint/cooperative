package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.Cooperative;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TemplateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String body;
    private OnlyIdRequest cooperative;
}