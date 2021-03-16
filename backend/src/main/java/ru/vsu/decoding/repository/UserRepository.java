package ru.vsu.decoding.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vsu.decoding.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
}
