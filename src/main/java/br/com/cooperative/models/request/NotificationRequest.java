package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.User;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String field;
    private User user;
}