package gr.alexc.otaobservatory.repository;

import gr.alexc.otaobservatory.entity.OtaVariable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtaVariableRepository extends JpaRepository<OtaVariable, Long> {
}
