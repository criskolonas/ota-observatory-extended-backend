package gr.alexc.otaobservatory.dto.stats.confiscations;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfiscationsForPrefectureForMonths {

    private LocalDate formMonthDate;
    private LocalDate toMonthDate;
    private List<ConfiscationsForPrefecturePerMonth> confiscationsPerMonth;

}
