package gr.alexc.otaobservatory.dto.stats.area;

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
public class AreaForPrefectureForMonths {
    private LocalDate formMonthDate;
    private LocalDate toMonthDate;
    private List<AreaForPrefecturePerMonth> areaPerMonth;
}
