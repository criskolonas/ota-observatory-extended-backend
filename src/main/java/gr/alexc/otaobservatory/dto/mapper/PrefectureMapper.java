package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureGeomDTO;
import gr.alexc.otaobservatory.dto.prefecture.PrefectureGeomSimpleDTO;
import gr.alexc.otaobservatory.entity.Prefecture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PrefectureCapitalMapper.class, OtaMapper.class})
public interface PrefectureMapper {

    PrefectureGeomSimpleDTO toPrefectureGeomSimpleDTO(Prefecture prefecture);

    List<PrefectureGeomSimpleDTO> toPrefectureGeomSimpleDTO(List<Prefecture> prefectureList);

    PrefectureGeomDTO toPrefectureGeomDTO(Prefecture prefecture);

}
