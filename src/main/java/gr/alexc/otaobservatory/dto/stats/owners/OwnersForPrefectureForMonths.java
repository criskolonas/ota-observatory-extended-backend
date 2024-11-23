package gr.alexc.otaobservatory.dto.stats.owners;
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
public class OwnersForPrefectureForMonths {
    private LocalDate formMonthDate;
    private LocalDate toMonthDate;
    private List<OwnersForPrefecturePerMonth> ownersPerMonth;
}
