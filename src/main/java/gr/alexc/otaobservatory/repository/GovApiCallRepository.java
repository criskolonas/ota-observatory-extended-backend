package gr.alexc.otaobservatory.repository;

import gr.alexc.otaobservatory.entity.GovApiCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GovApiCallRepository extends JpaRepository<GovApiCall, Long> {

    @Query("select gac from GovApiCall gac where gac.otaVariable = :otaVarType order by gac.toDate desc limit 1")
    Optional<GovApiCall> findLatestOfOtaVariableType(@Param("otaVarType") String otaVariableType);

}
