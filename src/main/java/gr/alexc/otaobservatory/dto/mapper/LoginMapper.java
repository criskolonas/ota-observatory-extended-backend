package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.LoginResponseDTO;
import gr.alexc.otaobservatory.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginResponseDTO toDto(User user);
}
