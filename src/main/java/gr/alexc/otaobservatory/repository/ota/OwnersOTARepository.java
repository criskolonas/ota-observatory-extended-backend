package gr.alexc.otaobservatory.repository.ota;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersByPrefecture;
import gr.alexc.otaobservatory.entity.ota.OwnersOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OwnersOTARepository extends JpaRepository<OwnersOTA, Long> {
    Optional<OwnersOTA> findById(Long id);

    @Query("select c from OwnersOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<OwnersOTA> getLastOwnerOTA(@Param("otaId") Long otaId);
    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(o2.owners) as "totalOwners" from ota_observatory.ownersota o2
            join ota_observatory.ota o on o2.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between o2.create_date and o2.last_check_date)
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    List<OwnersByPrefecture> getOwnersByPrefecturePerMonth(@Param("date") LocalDate lastMonthDayDate);

    @Query(value = """
            
            select p.id as "prefectureId", p.name_gr as "prefectureName", sum(o2.owners) as "totalOwners" from ota_observatory.ownersota o2
            join ota_observatory.ota o on o2.ota_id = o.id
            join ota_observatory.prefecture p on o.prefecture_id = p.id
            where (:date between o2.create_date and o2.last_check_date) and p.id = :prefectureId
            group by p.name_gr, p.id;
            """
            , nativeQuery = true)
    Optional<OwnersByPrefecture> getOwnersForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
            
            select o2.update_date as "lastUpdateDate" from ota_observatory.ownersota o2
            where (o2.last_check_date between :firstMonthDayDate and :lastMonthDayDate)
            limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastOwnerDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
            
            select o2.update_date as "lastUpdateDate" from ota_observatory.ownersota o2
            order by o2.update_date desc limit 1;
            """, nativeQuery = true)
    Optional<LocalDate> getLastOwnerDate();
}
