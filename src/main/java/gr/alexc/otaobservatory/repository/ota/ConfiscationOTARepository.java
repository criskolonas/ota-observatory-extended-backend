package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecture;
import gr.alexc.otaobservatory.entity.ota.ConfiscationOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConfiscationOTARepository extends JpaRepository<ConfiscationOTA, Long> {

    Optional<ConfiscationOTA> findById(Long id);

    @Query("select c from ConfiscationOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<ConfiscationOTA> getLastConfiscationOTA(@Param("otaId") Long otaId);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(c.confiscations) as "totalConfiscations" from ota_observatory.confiscationota c
            join ota_observatory.ota o on c.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between c.create_date and c.last_check_date)
            group by p.name_gr, p.id;
            """
    , nativeQuery = true)
    List<ConfiscationsByPrefecture> getConfiscationsByPrefecturePerMonth(@Param("date")LocalDate lastMonthDayDate);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(c.confiscations) as "totalConfiscations" from ota_observatory.confiscationota c
            join ota_observatory.ota o on c.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between c.create_date and c.last_check_date) and p.id = :prefectureId
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    Optional<ConfiscationsByPrefecture> getConfiscationsForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
            
            select c.update_date as "lastUpdateDate" from ota_observatory.confiscationota c
            where (c.last_check_date between :firstMonthDayDate and :lastMonthDayDate)
            limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastConfiscationDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
            
            select c.update_date as "lastUpdateDate" from ota_observatory.confiscationota c
            order by c.update_date desc limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastConfiscationDate();

}
