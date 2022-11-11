package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
