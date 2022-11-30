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
@JsonPropertyOrder({"id", "street", "number", "district", "complement", "city", "uf", "zipCode"})
public class AddressResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String street;
    private String number;
    private String district;
    private String city;
    private String uf;
    private String complemente;
    private String zipCode;
}
