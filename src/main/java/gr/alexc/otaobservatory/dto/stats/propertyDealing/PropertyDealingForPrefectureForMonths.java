package gr.alexc.otaobservatory.dto.stats.propertyDealing;

import gr.alexc.otaobservatory.dto.stats.properties.PropertiesForPrefecturePerMonth;
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
public class PropertyDealingForPrefectureForMonths {
    private LocalDate formMonthDate;
    private LocalDate toMonthDate;
    private List<PropertyDealingForPrefecturePerMonth> propertyDealingPerMonth;
}
