package br.com.cooperative.models.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParameterResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Double minimumLoanValue;
    private Double maximumLoanAmount;
    private Boolean active;
}