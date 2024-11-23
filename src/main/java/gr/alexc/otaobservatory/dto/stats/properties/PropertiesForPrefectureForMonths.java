package gr.alexc.otaobservatory.dto.stats.properties;

import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsForPrefecturePerMonth;
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
public class PropertiesForPrefectureForMonths {
    private LocalDate formMonthDate;
    private LocalDate toMonthDate;
    private List<PropertiesForPrefecturePerMonth> propertiesPerMonth;
}
