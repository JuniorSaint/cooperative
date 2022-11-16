package br.com.cooperative.models.entities;

import br.com.cooperative.models.entities.Cooperative;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Builder
@Entity
@Table(name = "templates")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Template implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(columnDefinition="TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name="cooperative_id")
    private Cooperative cooperative;
}