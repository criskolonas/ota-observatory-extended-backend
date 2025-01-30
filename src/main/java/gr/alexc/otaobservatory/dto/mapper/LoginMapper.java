package gr.alexc.otaobservatory.dto.mapper;
import gr.alexc.otaobservatory.dto.LoginDTO;
import gr.alexc.otaobservatory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    User logintoUserDTO(User result);
}
