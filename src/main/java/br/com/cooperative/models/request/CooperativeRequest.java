package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.*;
import br.com.cooperative.models.enums.CooperativeTypeEnum;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CooperativeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private CooperativeTypeEnum cooperativeType;
    private String cnpj;
    private OnlyIdRequest matrix;
    private Address address;
    private Contact contact;
}