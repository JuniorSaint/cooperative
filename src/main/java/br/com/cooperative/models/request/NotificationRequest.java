package br.com.cooperative.models.request;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String body;
    private Boolean wasRead;
    private Instant createdAt;
    private OnlyIdRequest user;
}