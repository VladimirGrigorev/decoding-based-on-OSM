package ru.vsu.gecoding.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vsu.gecoding.data.entity.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {

    Role findByName(String name);
}
