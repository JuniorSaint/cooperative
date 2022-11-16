package br.com.cooperative.models.Response;

import br.com.cooperative.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String body;
    private Boolean wasRead;
    private Instant createdAt;
}