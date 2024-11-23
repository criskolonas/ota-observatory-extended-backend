package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureCapitalDTO;
import gr.alexc.otaobservatory.entity.PrefectureCapital;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrefectureCapitalMapper {
    PrefectureCapitalDTO toPrefectureCapitalDTO(PrefectureCapital prefectureCapital);

    List<PrefectureCapitalDTO> toPrefectureCapitalDTO(List<PrefectureCapital> prefectureList);

}
