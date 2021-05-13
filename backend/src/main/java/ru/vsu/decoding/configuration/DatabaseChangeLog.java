package ru.vsu.decoding.configuration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.vsu.decoding.data.entity.Role;
import ru.vsu.decoding.data.entity.User;
import ru.vsu.decoding.data.repository.RoleRepository;
import ru.vsu.decoding.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "addRoles", author = "VGrigorev")
    public void addRoles(RoleRepository roleRepository) {
        List<Role> roleList = new ArrayList<>();

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        roleList.add(adminRole);
        roleList.add(userRole);

        roleRepository.insert(roleList);
    }

    @ChangeSet(order = "002", id = "addUsers", author = "VGrigorev")
    public void addUsers(UserRepository userRepository, RoleRepository roleRepository) {
        List<User> userList = new ArrayList<>();

        User admin = new User();
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        admin.setName("Admin");
        admin.setPassword("$2a$10$LcNC.bebj9mQ4VO1aAYghe1x7tncr8vDRcM3QPzxrxYtCp4BxSyP2");
        admin.setRoles(Collections.singletonList(adminRole));

        User user = new User();
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setName("User");
        user.setPassword("$2a$10$LcNC.bebj9mQ4VO1aAYghe1x7tncr8vDRcM3QPzxrxYtCp4BxSyP2");
        user.setRoles(Collections.singletonList(userRole));

        userList.add(admin);
        userList.add(user);

        userRepository.insert(userList);
    }

}
