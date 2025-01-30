package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    User registerToUser(User result);

}
