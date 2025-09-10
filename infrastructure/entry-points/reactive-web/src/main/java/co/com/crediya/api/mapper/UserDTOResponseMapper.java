package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.UserDTORequest;
import co.com.crediya.api.dto.UserDTOResponse;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOResponseMapper {

    UserDTOResponse toUserDto(User user);
}
