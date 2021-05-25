package ru.vsu.gecoding.service;

import org.springframework.stereotype.Service;
import ru.vsu.gecoding.data.entity.Question;
import ru.vsu.gecoding.data.entity.User;
import ru.vsu.gecoding.data.repository.QuestionRepository;
import ru.vsu.gecoding.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<Question> getAllQuestions() {
        var result = new ArrayList<Question>();
        questionRepository.findAll().forEach(result::add);
        return result;
    }

    public void setUserQuestions(ArrayList<String> questionsText, String username) {
        User user = userRepository.findByName(username);
        ArrayList<Question> questions = new ArrayList<>();
        for (String s : questionsText) {
            questions.add(questionRepository.findByText(s));
        }
        user.setInterests(questions);
        userRepository.save(user);
    }
}
