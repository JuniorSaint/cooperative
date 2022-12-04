package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.*;
import br.com.cooperative.models.enums.CooperativeTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CooperativeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;
    private CooperativeTypeEnum cooperativeType;
    private String cnpj;
    private CooperativeRequest matrix;
    private Address address;
    private Contact contact;
}