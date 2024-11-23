package gr.alexc.otaobservatory.service.stats;

import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecture;
import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsForPrefecturePerMonth;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.exception.VariableDataNotAvailable;
import gr.alexc.otaobservatory.repository.ota.ConfiscationOTARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfiscationStatsService {

    private final ConfiscationOTARepository confiscationOTARepository;
    private final StatsUtilsService statsUtilsService;

    public ConfiscationsByPrefecturePerMonth getConfiscationsByPrefecturePerMonth(Optional<LocalDate> dateOptional) {
        LocalDate searchDate;
        if (dateOptional.isPresent()) {
            searchDate = dateOptional.get();
        } else {
            searchDate = statsUtilsService.getLastMonthData(OTAVariable.CONFISCATION).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.CONFISCATION));
            searchDate = confiscationOTARepository.getLastConfiscationDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchDate),
                    statsUtilsService.getLastDayOfMonthDate(searchDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.CONFISCATION));
        }
        List<ConfiscationsByPrefecture> results = confiscationOTARepository.getConfiscationsByPrefecturePerMonth(searchDate)
                .stream().sorted(Comparator.comparing(ConfiscationsByPrefecture::getTotalConfiscations).reversed()).toList();
        return new ConfiscationsByPrefecturePerMonth(searchDate, results);
    }

    public ConfiscationsForPrefectureForMonths getConfiscationsForPrefecturePerMonth(
            Optional<LocalDate> fromDate,
            Optional<LocalDate> toDate,
            Long prefectureId
    ) {
        LocalDate searchFromDate = fromDate.orElse(statsUtilsService.getFirstDayOfYearDate(LocalDate.now()));

        LocalDate searchToDate;
        if (toDate.isPresent()) {
            searchToDate = toDate.get();
        } else {
            searchToDate = statsUtilsService.getLastMonthData(OTAVariable.CONFISCATION).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.CONFISCATION));
            searchToDate = confiscationOTARepository.getLastConfiscationDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchToDate),
                    statsUtilsService.getLastDayOfMonthDate(searchToDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.CONFISCATION));
        }

        List<LocalDate> searchDates = statsUtilsService.getMonthsList(searchFromDate, searchToDate);
        List<ConfiscationsForPrefecturePerMonth> confiscations = new ArrayList<>();
        for (LocalDate date : searchDates) {
            Optional<ConfiscationsByPrefecture> result = confiscationOTARepository.getConfiscationsForPrefecturePerMonth(
                    date,
                    prefectureId
            );
            if (result.isPresent()) {
                confiscations.add(new ConfiscationsForPrefecturePerMonth(date, result.get().getTotalConfiscations()));
            }
        }
        return new ConfiscationsForPrefectureForMonths(searchFromDate, searchToDate, confiscations);
    }

}
