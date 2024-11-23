package gr.alexc.otaobservatory.repository;

import gr.alexc.otaobservatory.entity.CadastralRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CadastralRegistrationStatusRepository extends JpaRepository<CadastralRegistrationStatus, Long> {

    @Query("select s from CadastralRegistrationStatus s where lower(trim(s.status)) = lower(trim(:description))")
    Optional<CadastralRegistrationStatus> findByDescription(@Param("description") String description);

}
