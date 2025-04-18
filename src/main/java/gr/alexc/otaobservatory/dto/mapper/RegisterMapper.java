package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.RegisterRequestDTO;
import gr.alexc.otaobservatory.dto.RegisterResponseDTO;
import gr.alexc.otaobservatory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "email", target = "email")
    })
    RegisterResponseDTO userToResponseDTO(User requestDTO);

}


