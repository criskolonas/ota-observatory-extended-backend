package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.OtaVariableDTO;
import gr.alexc.otaobservatory.entity.OtaVariable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OtaVariableMapper {

    OtaVariableDTO toDTO(OtaVariable o);

    List<OtaVariableDTO> toDTO(List<OtaVariable> o);

}
