package gr.alexc.otaobservatory.dto.stats.confiscations;

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
public class ConfiscationsByPrefecturePerMonth {

    private LocalDate monthDate;
    private List<ConfiscationsByPrefecture> confiscationsByPrefecture;

}
