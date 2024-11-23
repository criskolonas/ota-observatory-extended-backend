package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesByPrefecture;
import gr.alexc.otaobservatory.entity.ota.MortgageOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MortgageOTARepository extends JpaRepository<MortgageOTA, Long> {
    Optional<MortgageOTA> findById(Long id);

    // should check the id
    @Query("select c from MortgageOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<MortgageOTA> getLastMortgageOTA(@Param("otaId") Long otaId);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(m.mortgages) as "totalMortgages" from ota_observatory.mortgageota m
            join ota_observatory.ota o on m.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between m.create_date and m.last_check_date)
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    List<MortgagesByPrefecture> getMortgagesByPrefecturePerMonth(@Param("date") LocalDate lastMonthDayDate);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(m.mortgages) as "totalMortgages" from ota_observatory.mortgageota m
            join ota_observatory.ota o on m.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between m.create_date and m.last_check_date) and p.id = :prefectureId
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    Optional<MortgagesByPrefecture> getMortgagesForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
            
            select m.update_date as "lastUpdateDate" from ota_observatory.mortgageota m
            where (m.last_check_date between :firstMonthDayDate and :lastMonthDayDate)
            limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastMortgageDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
            
            select m.update_date as "lastUpdateDate" from ota_observatory.mortgageota m
            order by m.update_date desc limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastMortgageDate();

}
