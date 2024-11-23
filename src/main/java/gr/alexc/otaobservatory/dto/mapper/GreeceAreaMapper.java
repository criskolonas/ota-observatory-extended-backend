package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.GreeceAreaViewDTO;
import gr.alexc.otaobservatory.entity.view.GreeceAreaView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GreeceAreaMapper {

    GreeceAreaViewDTO toDto(GreeceAreaView view);

    List<GreeceAreaViewDTO> toDto(List<GreeceAreaView> views);

}
