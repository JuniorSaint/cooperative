package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.AgencyBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyBankRepository extends JpaRepository<AgencyBank, Long> {

    @Query("SELECT DISTINCT obj FROM AgencyBank obj WHERE " +
            " LOWER(obj.agency) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR LOWER(obj.cnpj) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR obj.bank.code LIKE CONCAT('%', :search, '%') ")
    Page<AgencyBank> findBySearch(@Param("search") String search, Pageable pageable);
}
