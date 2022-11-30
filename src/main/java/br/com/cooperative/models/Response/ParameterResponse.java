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
@JsonPropertyOrder({"id", "minimumLoanValue", "maximumLoanAmount", "active"})
public class ParameterResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Double minimumLoanValue;
    private Double maximumLoanAmount;
    private Boolean active;
}