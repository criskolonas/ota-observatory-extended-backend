package gr.alexc.otaobservatory.dto.stats.mortgages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MortgagesForPrefecturePerMonth {
    private LocalDate monthDate;
    private Long totalMortgages;
}
