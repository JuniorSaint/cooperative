package br.com.cooperative.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErros extends StandardError {
    private static final long serialVersionUID = 1L;
    private List<FieldMessage> erros = new ArrayList<>();

    public void addError(String fieldName, String message) {
        erros.add(new FieldMessage(fieldName, message));
    }
}
