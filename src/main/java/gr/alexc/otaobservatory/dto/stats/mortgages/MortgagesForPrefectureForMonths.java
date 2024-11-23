package gr.alexc.otaobservatory.dto.stats.mortgages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MortgagesForPrefectureForMonths {
    private LocalDate formMonthDate;
    private LocalDate toMonthDate;
    private List<MortgagesForPrefecturePerMonth> mortgagesPerMonth;
}
