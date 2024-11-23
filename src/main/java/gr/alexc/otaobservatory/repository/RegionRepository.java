package gr.alexc.otaobservatory.repository;

import gr.alexc.otaobservatory.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = """
        
        select count(*) as "totalOTA"
        from ota_observatory.region r
        join ota_observatory.prefecture p on r.id = p.region_id
        join ota_observatory.ota o on p.id = o.prefecture_id
        where r.id = :id;
"""
    , nativeQuery = true)
    Long getTotalOtaById(@Param("id") Long id);

}
