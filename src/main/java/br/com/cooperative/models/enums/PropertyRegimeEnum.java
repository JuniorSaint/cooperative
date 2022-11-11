package br.com.cooperative.models.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PropertyRegimeEnum {
    COMUNHAO_BENS(1, "comunhão de bens"),
    COMUNHAO_PARCIAL_BENS_(2, "comunhão parcial de bens"),
    COMUNHÃO_UNIVERSAL_BENS(3, "comunhão universal de bens"),
    SEPARACAO_BENS(4, "separação de bens"),
    SEPARACAO_OBRIGATORIA_BENS(4, "separação obrigatória de bens"),
    SEPARACAO_PARCIAL(5, "separação parcial"),
    OUTRO(6, "outro");

    private final Integer guid;
    private final String description;
}