package gr.alexc.otaobservatory.dto.stats.propertyDealing;

import gr.alexc.otaobservatory.dto.stats.properties.PropertiesByPrefecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDealingByPrefecturePerMonth {
    private LocalDate monthDate;
    private List<PropertyDealingByPrefecture> propertyDealingByPrefecture;
}
