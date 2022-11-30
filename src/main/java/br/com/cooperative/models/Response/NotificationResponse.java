package br.com.cooperative.models.Response;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String body;
    private Boolean wasRead;
    private UserResponse user;
    private Instant createdAt;
}