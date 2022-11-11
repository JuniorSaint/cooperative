package br.com.cooperative.repositories;

import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Permission;
import br.com.cooperative.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT obj FROM User obj " +
            " INNER JOIN obj.permissions permit WHERE " +
            " LOWER(obj.userName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR LOWER(obj.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR (COALESCE(:permissions) IS NULL OR permit IN :permissions) ")
    Page<User> findBySearch(@Param("search") String search, @Param("permissions") List<Permission> permissions, Pageable pageable);

    Optional<User> findByEmail(String email);
}
