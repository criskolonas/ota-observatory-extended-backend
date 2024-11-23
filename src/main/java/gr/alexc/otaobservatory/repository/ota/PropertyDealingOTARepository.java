package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecture;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingByPrefecture;
import gr.alexc.otaobservatory.entity.ota.PropertyDealingOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PropertyDealingOTARepository extends JpaRepository<PropertyDealingOTA, Long> {
    Optional<PropertyDealingOTA> findById(Long id);

    @Query("select c from PropertyDealingOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<PropertyDealingOTA> getLastPropertyDealingOTA(@Param("otaId") Long otaId);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(pd.property_deals) as "totalPropertyDealing" from  ota_observatory.property_dealingota pd
            join ota_observatory.ota o on pd.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between pd.create_date and pd.last_check_date)
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    List<PropertyDealingByPrefecture> getPropertyDealingByPrefecturePerMonth(@Param("date") LocalDate lastMonthDayDate);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(pd.property_deals) as "totalPropertyDealing" from  ota_observatory.property_dealingota pd
            join ota_observatory.ota o on pd.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between pd.create_date and pd.last_check_date) and p.id = :prefectureId
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    Optional<PropertyDealingByPrefecture> getPropertyDealingForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
            
            select pd.update_date as "lastUpdateDate" from  ota_observatory.property_dealingota pd
            where (pd.last_check_date between :firstMonthDayDate and :lastMonthDayDate)
            limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastPropertyDealingDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
            
            select pd.update_date as "lastUpdateDate" from ota_observatory.property_dealingota pd
            order by pd.update_date desc limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastPropertyDealingDate();
}
