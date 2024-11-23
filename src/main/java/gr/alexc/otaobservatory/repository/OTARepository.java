package gr.alexc.otaobservatory.repository;

import gr.alexc.otaobservatory.entity.OTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OTARepository extends JpaRepository<OTA, Long> {

    @Query("select o from OTA o where cast(o.otaId as integer ) = :otaId ")
    Optional<OTA> findOTAByOtaId(@Param("otaId") String otaId);

}
