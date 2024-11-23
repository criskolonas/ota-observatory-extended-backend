package gr.alexc.otaobservatory.dto.region;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureSimpleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegionDTO {
    private Long id;
    private String name;
    private Double unemploymentRate;
    private Long criminalityRate;
    private List<PrefectureSimpleDTO> prefectures;
}
