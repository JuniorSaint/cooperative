package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
}
