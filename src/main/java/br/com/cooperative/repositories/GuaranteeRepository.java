package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, UUID> {
}
