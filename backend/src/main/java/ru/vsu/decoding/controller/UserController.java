package ru.vsu.decoding.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.decoding.data.dto.UserDto;
import ru.vsu.decoding.data.mapper.UserMapper;
import ru.vsu.decoding.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "profile")
    public UserDto getProfile(Authentication authentication) {
        String currentPrincipalName = authentication.getName();
        var user = userService.findByLogin(currentPrincipalName);
        return userMapper.userToUserDto(user);
    }
}
