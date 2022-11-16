package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class GuaranteeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String description;
    private Double value;
}
