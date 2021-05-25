package ru.vsu.gecoding.data.dto.security;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String login;
    private String password;
}
