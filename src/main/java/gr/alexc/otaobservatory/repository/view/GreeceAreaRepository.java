package gr.alexc.otaobservatory.repository.view;

import gr.alexc.otaobservatory.entity.view.GreeceAreaView;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GreeceAreaRepository extends ReadOnlyRepository<GreeceAreaView, Long> {

    @Query("select ar from GreeceAreaView ar order by ar.type desc")
    List<GreeceAreaView> findAllAreas();

}
