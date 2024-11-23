package gr.alexc.otaobservatory.dto.stats.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaForPrefecturePerMonth {
    private LocalDate monthDate;
    private Long totalArea;
}
