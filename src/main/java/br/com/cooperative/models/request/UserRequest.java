package br.com.cooperative.models.request;

import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.Notification;
import br.com.cooperative.models.entities.Permission;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserRequest implements  Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String email;
    private String password;
    private Boolean active;
    private String cpf;
    private List<OnlyIdRequest> permissions;
    private OnlyIdRequest cooperative;
}