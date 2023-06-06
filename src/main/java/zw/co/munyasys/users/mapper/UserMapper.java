package zw.co.munyasys.users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import zw.co.munyasys.users.dto.UserDto;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.create.CreateUserRequest;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", source = "email")
    User toEntity(CreateUserRequest createUserRequest);
}
