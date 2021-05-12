package ru.vsu.decoding.data.dto.security;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String login;
    private String password;
}
