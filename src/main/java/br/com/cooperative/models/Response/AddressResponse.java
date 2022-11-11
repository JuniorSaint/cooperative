package br.com.cooperative.models.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String street;
    private String number;
    private String district;
    private String city;
    private String uf;
    private String complement;
    private String zipCode;
}
