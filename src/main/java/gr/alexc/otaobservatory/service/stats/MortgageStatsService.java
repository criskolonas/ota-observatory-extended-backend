package gr.alexc.otaobservatory.service.stats;
import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesByPrefecture;
import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesForPrefecturePerMonth;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.exception.VariableDataNotAvailable;
import gr.alexc.otaobservatory.repository.ota.MortgageOTARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MortgageStatsService {
    private final MortgageOTARepository mortgageOTARepository;
    private final StatsUtilsService statsUtilsService;

    public MortgagesByPrefecturePerMonth getMortgagesByPrefecturePerMonth(Optional<LocalDate> dateOptional) {
        LocalDate searchDate;
        if (dateOptional.isPresent()) {
            searchDate = dateOptional.get();
        } else {
            searchDate = statsUtilsService.getLastMonthData(OTAVariable.MORTGAGES).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.MORTGAGES));
            searchDate = mortgageOTARepository.getLastMortgageDateForMonth(
                    statsUtilsService.getFirstDayOfMonthDate(searchDate),
                    statsUtilsService.getLastDayOfMonthDate(searchDate)
            ).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.MORTGAGES));
        }
        List<MortgagesByPrefecture> results = mortgageOTARepository.getMortgagesByPrefecturePerMonth(searchDate)
                .stream().sorted(Comparator.comparing(MortgagesByPrefecture::getTotalMortgages).reversed()).toList();
        return new MortgagesByPrefecturePerMonth(searchDate, results);
    }

    public MortgagesForPrefectureForMonths getMortgagesForPrefecturePerMonth(
            Optional<LocalDate> fromDate,
            Optional<LocalDate> toDate,
            Long prefectureId
    ) {
        LocalDate searchFromDate = fromDate.orElse(statsUtilsService.getFirstDayOfYearDate(LocalDate.now()));

        LocalDate searchToDate;
        if (toDate.isPresent()) {
            searchToDate = toDate.get();
        } else {
            searchToDate = statsUtilsService.getLastMonthData(OTAVariable.MORTGAGES).orElseThrow(() -> new VariableDataNotAvailable(OTAVariable.MORTGAGES));
            System.out.println(searchToDate);

        }

        List<LocalDate> searchDates = statsUtilsService.getMonthsList(searchFromDate, searchToDate);
        List<MortgagesForPrefecturePerMonth> mortgages = new ArrayList<>();
        for (LocalDate date : searchDates) {
            Optional<MortgagesByPrefecture> result = mortgageOTARepository.getMortgagesForPrefecturePerMonth(
                    date,
                    prefectureId
            );
            if (result.isPresent()) {
                mortgages.add(new MortgagesForPrefecturePerMonth(date, result.get().getTotalMortgages()));
            }
        }
        return new MortgagesForPrefectureForMonths(searchFromDate, searchToDate, mortgages);
    }
}
