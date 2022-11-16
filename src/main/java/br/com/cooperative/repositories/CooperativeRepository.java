package br.com.cooperative.repositories;

import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.Response.CooperativeResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Cooperative;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CooperativeRepository extends JpaRepository<Cooperative, UUID> {

    @Query("SELECT DISTINCT obj FROM Cooperative obj WHERE " +
            " LOWER(obj.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            " OR LOWER(obj.cooperativeType) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR obj.cnpj LIKE CONCAT('%', :search, '%') ")
    Page<Cooperative> findBySearch(@Param("search") String search, Pageable pageable);
}
