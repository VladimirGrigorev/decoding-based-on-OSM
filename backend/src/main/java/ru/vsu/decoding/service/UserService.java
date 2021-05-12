package ru.vsu.decoding.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.decoding.data.dto.security.RegisterUserDto;
import ru.vsu.decoding.data.entity.User;
import ru.vsu.decoding.exeption.NotFoundException;
import ru.vsu.decoding.data.repository.RoleRepository;
import ru.vsu.decoding.data.repository.UserRepository;
import ru.vsu.decoding.exeption.WrongDataException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        var result = new ArrayList<User>();
        userRepository.findAll().forEach(result::add);
        return result;
    }

    public User getUser(BigInteger id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void registerUser(RegisterUserDto dto, String roleName) {
        if(userRepository.findByName(dto.getLogin()) != null)
            throw new WrongDataException();
        if(roleRepository.findByName(roleName) == null)
            throw new WrongDataException();
        var userRole = roleRepository.findByName(roleName);
        var user = new User();
        user.setName(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }

    public User findByLoginAndPassword(String login, String password) {
        var user = userRepository.findByName(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User findByLogin(String login) {
        if(userRepository.findByName(login) == null)
            throw new NotFoundException();
        return userRepository.findByName(login);
    }
}
