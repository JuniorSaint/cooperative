package br.com.cooperative.models.enums;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum GenderTypeEnum {
    MASCULINO(1, "masculino"),
    FEMININO(2, "feminino"),
    NEUTRO(3, "neutro");

    private final Integer guid;
    private final String description;
}
