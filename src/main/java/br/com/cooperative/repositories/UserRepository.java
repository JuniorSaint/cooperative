package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.Role;
import br.com.cooperative.models.entities.User;
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
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT DISTINCT obj FROM User obj " +
            " INNER JOIN obj.roles permit WHERE " +
            " LOWER(obj.userName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR LOWER(obj.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            " OR (COALESCE(:permissions) IS NULL OR permit IN :permissions) ")
    Page<User> findBySearch(@Param("search") String search, @Param("permissions") List<Role> permissions, Pageable pageable);

    Optional<User> findByEmail(String email);
}
