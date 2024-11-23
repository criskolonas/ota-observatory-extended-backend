package gr.alexc.otaobservatory.repository;

import gr.alexc.otaobservatory.entity.Prefecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrefectureRepository extends JpaRepository<Prefecture, Long> {

    List<Prefecture> findAllByRegionId(Long regionId);

}
