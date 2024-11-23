package gr.alexc.otaobservatory.service.stats;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesByPrefecture;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesForPrefecturePerMonth;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.exception.VariableDataNotAvailable;
import gr.alexc.otaobservatory.repository.ota.PropertiesOTARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertiesStatsService {
    private final PropertiesOTARepository propertiesOTARepository;
    private final StatsUtilsService statsUtilsService;

    public PropertiesByPrefecturePerMonth getPropertiesByPrefecturePerMonth(Optional<LocalDate> dateOptional) {
        LocalDate searchDate;
        if (dateOptional.isPresent()) {
            searchDate = dateOptional.get();
        } else {
            searchDate = LocalDate.of(2024, Month.JANUARY,5);

        }
        List<PropertiesByPrefecture> results = propertiesOTARepository.getPropertiesByPrefecturePerMonth(searchDate)
                .stream().sorted(Comparator.comparing(PropertiesByPrefecture::getTotalProperties).reversed()).toList();
        return new PropertiesByPrefecturePerMonth(searchDate, results);
    }

    public PropertiesForPrefectureForMonths getPropertiesForPrefecturePerMonth(
            Optional<LocalDate> fromDate,
            Optional<LocalDate> toDate,
            Long prefectureId
    ) {
        LocalDate searchFromDate = fromDate.orElse(statsUtilsService.getFirstDayOfYearDate(LocalDate.now()));

        LocalDate searchToDate;
        if (toDate.isPresent()) {
            searchToDate = toDate.get();
        } else {
            searchToDate = statsUtilsService.getLastMonthData(OTAVariable.PROPERTIES).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.PROPERTIES));
            searchToDate = propertiesOTARepository.getLastPropertiesDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchToDate),
                    statsUtilsService.getLastDayOfMonthDate(searchToDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.PROPERTIES));
        }

        List<LocalDate> searchDates = statsUtilsService.getMonthsList(searchFromDate, searchToDate);
        List<PropertiesForPrefecturePerMonth> properties = new ArrayList<>();
        for (LocalDate date : searchDates) {
            Optional<PropertiesByPrefecture> result = propertiesOTARepository.getPropertiesForPrefecturePerMonth(
                    date,
                    prefectureId
            );
            if (result.isPresent()) {
                properties.add(new PropertiesForPrefecturePerMonth(date, result.get().getTotalProperties()));
            }
        }
        return new PropertiesForPrefectureForMonths(searchFromDate, searchToDate, properties);
    }
}
