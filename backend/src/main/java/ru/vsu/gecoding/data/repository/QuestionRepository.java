package ru.vsu.gecoding.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vsu.gecoding.data.entity.Question;

import java.math.BigInteger;

public interface QuestionRepository extends MongoRepository<Question, BigInteger> {

    Question findByText(String text);
}
