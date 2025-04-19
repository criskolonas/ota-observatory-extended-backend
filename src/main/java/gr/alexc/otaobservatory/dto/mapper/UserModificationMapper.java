package gr.alexc.otaobservatory.dto.mapper;


import gr.alexc.otaobservatory.dto.UserModificationPermissionsResponseDTO;
import gr.alexc.otaobservatory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserModificationMapper {
    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "is_admin", target = "is_admin")
    })
    List<UserModificationPermissionsResponseDTO> userToPermissionsReq(List<User> user);

}


