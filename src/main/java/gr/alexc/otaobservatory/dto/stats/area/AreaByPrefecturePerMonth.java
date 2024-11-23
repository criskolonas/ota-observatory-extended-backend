package gr.alexc.otaobservatory.dto.stats.area;
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
public class AreaByPrefecturePerMonth {
    private LocalDate monthDate;
    private List<AreaByPrefecture> areaByPrefecture;
}
