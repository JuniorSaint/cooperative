package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String body;
    private Boolean wasRead;
    private Instant createdAt;
    private OnlyIdRequest user;
}