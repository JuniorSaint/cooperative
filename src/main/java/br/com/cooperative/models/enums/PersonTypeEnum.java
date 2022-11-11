package br.com.cooperative.models.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PersonTypeEnum {
    FISICA(1, "física"),
    JURIDICA(2, "jurídica");

    private final Integer guid;
    private final String description;
}