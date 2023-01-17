package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

//    @Query("SELECT DISTINCT obj FROM User obj " +
//            " LOWER(obj.userName) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            " OR LOWER(obj.email) LIKE LOWER(CONCAT('%', :search, '%')) " )
//    Page<User> findBySearch(@Param("search") String search, Pageable pageable);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}