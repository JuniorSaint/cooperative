package br.com.cooperative.repositories;

import br.com.cooperative.models.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query(value = "SELECT obj FROM Notification obj WHERE " +
            "obj.user.id = :idUser  AND " +
            "obj.wasRead = :wasRead AND " +
            "obj.createdAt BETWEEN :dateInicial AND :dateFinal" +
//            "order by obj.createdAt desc" +
            "")
    Page<Notification> findNotificatioin(@Param(value = "idUser") UUID idUser,
                                         @Param(value = "wasRead") Boolean wasRead,
                                         @Param(value = "dateInicial") LocalDate dateInicial,
                                         @Param(value = "dateFinal") LocalDate dateFinal, Pageable pageable);
}
