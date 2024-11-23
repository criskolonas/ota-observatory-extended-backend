package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.ota.OtaDTO;
import gr.alexc.otaobservatory.entity.OTA;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtaMapper {

    OtaDTO otaToOtaDTO(OTA ota);

}
