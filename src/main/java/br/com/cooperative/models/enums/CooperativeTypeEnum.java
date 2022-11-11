package br.com.cooperative.models.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CooperativeTypeEnum {
    MATRIX(1, "matrix"),
    BRANCH(2, "branch");

    private final Integer guid;
    private final String description;
}