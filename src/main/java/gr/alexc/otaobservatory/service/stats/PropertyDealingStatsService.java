package gr.alexc.otaobservatory.service.stats;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingByPrefecture;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingForPrefecturePerMonth;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.exception.VariableDataNotAvailable;
import gr.alexc.otaobservatory.repository.ota.PropertyDealingOTARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyDealingStatsService {
    private final PropertyDealingOTARepository propertyDealingOTARepository;
    private final StatsUtilsService statsUtilsService;

    public PropertyDealingByPrefecturePerMonth getPropertyDealingByPrefecturePerMonth(Optional<LocalDate> dateOptional) {
        LocalDate searchDate;
        if (dateOptional.isPresent()) {
            searchDate = dateOptional.get();
        } else {
            searchDate = statsUtilsService.getLastMonthData(OTAVariable.PROPERTY_DEALING).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.PROPERTY_DEALING));
            searchDate = propertyDealingOTARepository.getLastPropertyDealingDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchDate),
                    statsUtilsService.getLastDayOfMonthDate(searchDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.PROPERTY_DEALING));
        }
        List<PropertyDealingByPrefecture> results = propertyDealingOTARepository.getPropertyDealingByPrefecturePerMonth(searchDate)
                .stream().sorted(Comparator.comparing(PropertyDealingByPrefecture::getTotalPropertyDealing).reversed()).toList();
        return new PropertyDealingByPrefecturePerMonth(searchDate, results);
    }

    public PropertyDealingForPrefectureForMonths getPropertyDealingForPrefecturePerMonth(
            Optional<LocalDate> fromDate,
            Optional<LocalDate> toDate,
            Long prefectureId
    ) {
        LocalDate searchFromDate = fromDate.orElse(statsUtilsService.getFirstDayOfYearDate(LocalDate.now()));

        LocalDate searchToDate;
        if (toDate.isPresent()) {
            searchToDate = toDate.get();
        } else {
            searchToDate = statsUtilsService.getLastMonthData(OTAVariable.PROPERTY_DEALING).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.PROPERTY_DEALING));
            searchToDate = propertyDealingOTARepository.getLastPropertyDealingDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchToDate),
                    statsUtilsService.getLastDayOfMonthDate(searchToDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.PROPERTY_DEALING));
        }

        List<LocalDate> searchDates = statsUtilsService.getMonthsList(searchFromDate, searchToDate);
        List<PropertyDealingForPrefecturePerMonth> propertyDealing = new ArrayList<>();
        for (LocalDate date : searchDates) {
            Optional<PropertyDealingByPrefecture> result = propertyDealingOTARepository.getPropertyDealingForPrefecturePerMonth(
                    date,
                    prefectureId
            );
            if (result.isPresent()) {
                propertyDealing.add(new PropertyDealingForPrefecturePerMonth(date, result.get().getTotalPropertyDealing()));
            }
        }
        return new PropertyDealingForPrefectureForMonths(searchFromDate, searchToDate, propertyDealing);
    }
}
