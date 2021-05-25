package ru.vsu.gecoding.data.mapper;

import org.mapstruct.Mapper;
import ru.vsu.gecoding.data.dto.UserDto;
import ru.vsu.gecoding.data.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserDto userToUserDto(User user);
    List<UserDto> userToUserDto(List<User> users);
}
