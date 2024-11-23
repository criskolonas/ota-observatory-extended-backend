package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.dto.stats.cadastral.CadastralByPrefecture;
import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecture;
import gr.alexc.otaobservatory.entity.ota.CadastralRegistrationStatusOTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CadastralStatusOTARepository extends JpaRepository<CadastralRegistrationStatusOTA, Long> {
    Optional<CadastralRegistrationStatusOTA> findById(Long id);

    @Query("select c from CadastralRegistrationStatusOTA c where cast(c.ota.otaId as integer) = :otaId order by c.lastCheckDate desc limit 1")
    Optional<CadastralRegistrationStatusOTA> getLastCadastralStatusOTA(@Param("otaId") Long otaId);

    @Query(value = """

            select\s
                                         p.id as "prefectureId"
                                         p.name_gr as "prefectureName"
                                         crs.status as "cadastralStatus"
                                         count(*) as "statusCount"
                                     from
                                         cadastral_registration_statusota crso
                                     join\s
                                         ota o on crso.ota_id = o.id
                                     join\s
                                         prefecture p on o.prefecture_id = p.id
                                     join\s
                                         cadastral_registration_status crs on crso.status_id = crs.id
                                     where\s
                                         ('2024-01-11' between crso.create_date and crso.last_check_date)
                                     group by\s
                                         p.name_gr, p.id, crs.status;
            """
            , nativeQuery = true)
    List<CadastralByPrefecture> getCadastralByPrefecturePerMonth(@Param("date") LocalDate lastMonthDayDate);

    @Query(value = """
    select p.id as "prefectureId", 
           p.name_gr as "prefectureName", 
           crs.status as "cadastralStatus", 
           count(*) as "statusCount"
    from cadastral_registration_statusota crso
    join ota o on crso.ota_id = o.id
    join prefecture p on o.prefecture_id = p.id
    join cadastral_registration_status crs on crso.status_id = crs.id
    where (:date between crso.create_date and crso.last_check_date)
      and p.id = :prefectureId
    group by p.name_gr, p.id, crs.status;
    """
            , nativeQuery = true)
    Optional<CadastralByPrefecture> getCadastralForPrefecturePerMonth(
            @Param("date")LocalDate lastMonthDayDate,
            @Param("prefectureId") Long prefectureId
    );

    @Query(value = """
    select crso.update_date as "lastUpdateDate" 
    from cadastral_registration_statusota crso
    where crso.last_check_date between :firstMonthDayDate and :lastMonthDayDate
    limit 1;
    """, nativeQuery = true)
    Optional<LocalDate> getLastCadastralDateForMonth(
            @Param("firstMonthDayDate") LocalDate firstMonthDayDate,
            @Param("lastMonthDayDate") LocalDate lastMonthDayDate
    );


    @Query(value = """
    select crso.update_date as "lastUpdateDate" 
    from cadastral_registration_statusota crso
    order by crso.update_date desc 
    limit 1;
    """, nativeQuery = true)
    Optional<LocalDate> getLastCadastralDate();



}
