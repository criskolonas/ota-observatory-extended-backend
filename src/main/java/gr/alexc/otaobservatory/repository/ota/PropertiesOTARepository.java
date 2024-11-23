package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecture;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesByPrefecture;
import gr.alexc.otaobservatory.entity.ota.PropertiesOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PropertiesOTARepository extends JpaRepository<PropertiesOTA, Long> {
    Optional<PropertiesOTA> findById(Long id);

    @Query("select c from PropertiesOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<PropertiesOTA> getLastPropertyOTA(@Param("otaId") Long otaId);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(p2.properties) as "totalProperties" from ota_observatory.propertiesota p2
            join ota_observatory.ota o on p2.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between p2.create_date and p2.last_check_date)
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    List<PropertiesByPrefecture> getPropertiesByPrefecturePerMonth(@Param("date") LocalDate lastMonthDayDate);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(p2.properties) as "totalProperties" from ota_observatory.propertiesota p2
            join ota_observatory.ota o on p2.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between p2.create_date and p2.last_check_date) and p.id = :prefectureId
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    Optional<PropertiesByPrefecture> getPropertiesForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
            
            select p2.update_date as "lastUpdateDate" from ota_observatory.propertiesota p2
            where (p2.last_check_date between :firstMonthDayDate and :lastMonthDayDate)
            limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastPropertiesDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
            
            select p2.update_date as "lastUpdateDate" from ota_observatory.propertiesota p2
            order by p2.update_date desc limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastPropertiesDate();
}
