package ru.vsu.decoding.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.vsu.decoding.data.dto.UserDto;
import ru.vsu.decoding.data.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserDto userToUserDto(User user);
    List<UserDto> userToUserDto(List<User> users);
}
