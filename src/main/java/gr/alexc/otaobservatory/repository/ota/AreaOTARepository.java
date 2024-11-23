package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.dto.stats.area.AreaByPrefecture;
import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecture;
import gr.alexc.otaobservatory.entity.ota.AreaOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AreaOTARepository extends JpaRepository<AreaOTA, Long> {
    Optional<AreaOTA> findById(Long id);

    // should check the id
    @Query("select c from AreaOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<AreaOTA> getLastAreaOTA(@Param("otaId") Long otaId);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(a.area) as "totalAreas" from ota_observatory.areaota a
            join ota_observatory.ota o on a.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between a.create_date and a.last_check_date)
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    List<AreaByPrefecture> getAreaByPrefecturePerMonth(@Param("date")LocalDate lastMonthDayDate);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(a.area) as "totalAreas" from ota_observatory.areaota a
            join ota_observatory.ota o on a.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between a.create_date and a.last_check_date) and p.id = :prefectureId
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    Optional<AreaByPrefecture> getAreaForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
            
            select a.update_date as "lastUpdateDate" from ota_observatory.areaota a
            where (a.last_check_date between :firstMonthDayDate and :lastMonthDayDate)
            limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastAreaDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
            
            select a.update_date as "lastUpdateDate" from ota_observatory.areaota a
            order by a.update_date desc limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastAreaDate();
}
