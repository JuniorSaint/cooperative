package br.com.cooperative.models.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MaritalStatusEnum {
    CASADO(1, "casado"),
    DIVORCIADO(2, "divorciado"),
    SOLTEIRO(3, "solteiro"),
    VIUVO(4, "viúvo"),
    OUTROS(4, "outros");

    private final Integer guid;
    private final String description;
}