package ru.vsu.decoding.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vsu.decoding.data.entity.User;

import java.math.BigInteger;

public interface UserRepository extends MongoRepository<User, BigInteger> {

    User findByName(String name);
}
