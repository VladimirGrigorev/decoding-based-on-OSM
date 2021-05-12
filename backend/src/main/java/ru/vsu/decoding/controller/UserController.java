package ru.vsu.decoding.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.decoding.data.dto.UserDto;
import ru.vsu.decoding.data.mapper.UserMapper;
import ru.vsu.decoding.service.UserService;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "")
    public List<UserDto> getUsers() {
        return userMapper.userToUserDto(userService.getAllUsers());
    }

    @GetMapping(path = "/{id}")
    public UserDto getUser(@PathVariable BigInteger id) {
        return userMapper.userToUserDto(userService.getUser(id));
    }
}
