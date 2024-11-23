package gr.alexc.otaobservatory.service.stats;
import gr.alexc.otaobservatory.dto.stats.area.AreaByPrefecture;
import gr.alexc.otaobservatory.dto.stats.area.AreaByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.area.AreaForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.area.AreaForPrefecturePerMonth;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.exception.VariableDataNotAvailable;
import gr.alexc.otaobservatory.repository.ota.AreaOTARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaStatsService {
    private final AreaOTARepository areaOTARepository;
    private final StatsUtilsService statsUtilsService;

    public AreaByPrefecturePerMonth getAreaByPrefecturePerMonth(Optional<LocalDate> dateOptional) {
        LocalDate searchDate;
        if (dateOptional.isPresent()) {
            searchDate = dateOptional.get();
        } else {
            searchDate = statsUtilsService.getLastMonthData(OTAVariable.AREA).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.AREA));
            searchDate = areaOTARepository.getLastAreaDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchDate),
                    statsUtilsService.getLastDayOfMonthDate(searchDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.AREA));
        }
        List<AreaByPrefecture> results = areaOTARepository.getAreaByPrefecturePerMonth(searchDate)
                .stream().sorted(Comparator.comparing(AreaByPrefecture::getTotalAreas).reversed()).toList();
        return new AreaByPrefecturePerMonth(searchDate, results);
    }

    public AreaForPrefectureForMonths getAreaForPrefecturePerMonth(
            Optional<LocalDate> fromDate,
            Optional<LocalDate> toDate,
            Long prefectureId
    ) {
        LocalDate searchFromDate = fromDate.orElse(statsUtilsService.getFirstDayOfYearDate(LocalDate.now()));

        LocalDate searchToDate;
        if (toDate.isPresent()) {
            searchToDate = toDate.get();
        } else {
            searchToDate = statsUtilsService.getLastMonthData(OTAVariable.AREA).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.AREA));
            searchToDate = areaOTARepository.getLastAreaDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchToDate),
                    statsUtilsService.getLastDayOfMonthDate(searchToDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.AREA));
        }

        List<LocalDate> searchDates = statsUtilsService.getMonthsList(searchFromDate, searchToDate);
        List<AreaForPrefecturePerMonth> area = new ArrayList<>();
        for (LocalDate date : searchDates) {
            Optional<AreaByPrefecture> result = areaOTARepository.getAreaForPrefecturePerMonth(
                    date,
                    prefectureId
            );
            if (result.isPresent()) {
                area.add(new AreaForPrefecturePerMonth(date, result.get().getTotalAreas()));
            }
        }
        return new AreaForPrefectureForMonths(searchFromDate, searchToDate, area);
    }
}
