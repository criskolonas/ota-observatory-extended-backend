package gr.alexc.otaobservatory.dto.stats.owners;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnersForPrefecturePerMonth {
    private LocalDate monthDate;
    private Long totalOwners;
}
