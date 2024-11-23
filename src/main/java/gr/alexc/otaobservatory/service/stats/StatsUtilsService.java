package gr.alexc.otaobservatory.service.stats;

import gr.alexc.otaobservatory.entity.GovApiCall;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.repository.GovApiCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsUtilsService {

    private final GovApiCallRepository govApiCallRepository;

    public List<LocalDate> getMonthsList(LocalDate from, LocalDate to) {
        LocalDate fromDate = from.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate toDate = to.with(TemporalAdjusters.lastDayOfMonth());
        long months = ChronoUnit.MONTHS.between(
                fromDate,
                toDate
        ) + 1;
        List<LocalDate> monthsList = new ArrayList<>();
        monthsList.add(fromDate);
        for (int i = 1; i <= months; i++) {
            monthsList.add(fromDate.plusMonths(i));
        }
        return monthsList;
    }

    public Optional<LocalDate> getLastMonthData(OTAVariable variable) {
        Optional<GovApiCall> apiCallOptional = govApiCallRepository.findLatestOfOtaVariableType(variable.name());
        return apiCallOptional.map(GovApiCall::getToDate);
    }

    public LocalDate getLastDayOfMonthDate(LocalDate date){
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDate getFirstDayOfMonthDate(LocalDate date){
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate getFirstDayOfYearDate(LocalDate date){
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

}
