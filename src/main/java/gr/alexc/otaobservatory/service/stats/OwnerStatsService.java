package gr.alexc.otaobservatory.service.stats;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersByPrefecture;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersForPrefecturePerMonth;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.exception.VariableDataNotAvailable;
import gr.alexc.otaobservatory.repository.ota.ConfiscationOTARepository;
import gr.alexc.otaobservatory.repository.ota.OwnersOTARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerStatsService {
    private final OwnersOTARepository ownersOTARepository;

    private final StatsUtilsService statsUtilsService;

    public OwnersByPrefecturePerMonth getOwnersByPrefecturePerMonth(Optional<LocalDate> dateOptional) {
        LocalDate searchDate;
        if (dateOptional.isPresent()) {
            searchDate = dateOptional.get();
        } else {
            searchDate = statsUtilsService.getLastMonthData(OTAVariable.OWNERS).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.OWNERS));
            searchDate = ownersOTARepository.getLastOwnerDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchDate),
                    statsUtilsService.getLastDayOfMonthDate(searchDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.OWNERS));
        }
        List<OwnersByPrefecture> results = ownersOTARepository.getOwnersByPrefecturePerMonth(searchDate)
                .stream().sorted(Comparator.comparing(OwnersByPrefecture::getTotalOwners).reversed()).toList();
        return new OwnersByPrefecturePerMonth(searchDate, results);
    }

    public OwnersForPrefectureForMonths getOwnersForPrefecturePerMonth(
            Optional<LocalDate> fromDate,
            Optional<LocalDate> toDate,
            Long prefectureId
    ) {
        LocalDate searchFromDate = fromDate.orElse(statsUtilsService.getFirstDayOfYearDate(LocalDate.now()));

        LocalDate searchToDate;
        if (toDate.isPresent()) {
            searchToDate = toDate.get();
        } else {
            searchToDate = statsUtilsService.getLastMonthData(OTAVariable.OWNERS).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.OWNERS));
            searchToDate = ownersOTARepository.getLastOwnerDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchToDate),
                    statsUtilsService.getLastDayOfMonthDate(searchToDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.OWNERS));
        }

        List<LocalDate> searchDates = statsUtilsService.getMonthsList(searchFromDate, searchToDate);
        List<OwnersForPrefecturePerMonth> owners = new ArrayList<>();
        for (LocalDate date : searchDates) {
            Optional<OwnersByPrefecture> result = ownersOTARepository.getOwnersForPrefecturePerMonth(
                    date,
                    prefectureId
            );
            if (result.isPresent()) {
                owners.add(new OwnersForPrefecturePerMonth(date, result.get().getTotalOwners()));
            }
        }
        return new OwnersForPrefectureForMonths(searchFromDate, searchToDate, owners);
    }
}
