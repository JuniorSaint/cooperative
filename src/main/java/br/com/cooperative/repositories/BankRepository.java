package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    List<Bank> findAllByCode(String search);
}
