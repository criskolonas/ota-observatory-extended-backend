package gr.alexc.otaobservatory.dto.stats.confiscations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfiscationsForPrefecturePerMonth {

    private LocalDate monthDate;
    private Long totalConfiscations;

}
