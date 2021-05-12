package ru.vsu.decoding.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vsu.decoding.data.entity.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {

    Role findByName(String name);
}
