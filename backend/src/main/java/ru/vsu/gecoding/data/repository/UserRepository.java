package ru.vsu.gecoding.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vsu.gecoding.data.entity.User;

import java.math.BigInteger;

public interface UserRepository extends MongoRepository<User, BigInteger> {

    User findByName(String name);
}
