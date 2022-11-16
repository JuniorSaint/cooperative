package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ParameterRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Double minimumLoanValue;
    private Double maximumLoanAmount;
    private Boolean active;
}