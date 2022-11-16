package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AddressRequest implements Serializable {
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
