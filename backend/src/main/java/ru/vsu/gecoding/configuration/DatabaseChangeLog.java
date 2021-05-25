package ru.vsu.gecoding.configuration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.vsu.gecoding.data.entity.Question;
import ru.vsu.gecoding.data.entity.Role;
import ru.vsu.gecoding.data.entity.User;
import ru.vsu.gecoding.data.repository.QuestionRepository;
import ru.vsu.gecoding.data.repository.RoleRepository;
import ru.vsu.gecoding.data.repository.UserRepository;

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

    @ChangeSet(order = "003", id = "addQuestions", author = "VGrigorev")
    public void addQuestions(QuestionRepository questionRepository) {
        List<Question> questionList = new ArrayList<>();

        Question question1 = new Question();
        question1.setText("Книги");
        question1.setTag("shop");
        question1.setTagValue("books");
        questionList.add(question1);

        Question question2 = new Question();
        question2.setText("Спорт");
        question2.setTag("shop");
        question2.setTagValue("sports");
        questionList.add(question2);

        Question question3 = new Question();
        question3.setText("Рестораны");
        question3.setTag("amenity");
        question3.setTagValue("restaurant");
        questionList.add(question3);

        Question question4 = new Question();
        question4.setText("Кафе");
        question4.setTag("amenity");
        question4.setTagValue("cafe");
        questionList.add(question4);

        Question question5 = new Question();
        question5.setText("Кино");
        question5.setTag("amenity");
        question5.setTagValue("cinema");
        questionList.add(question5);

        Question question6 = new Question();
        question6.setText("Фото");
        question6.setTag("shop");
        question6.setTagValue("photo");
        questionList.add(question6);

        Question question7 = new Question();
        question7.setText("Тренажерный зал");
        question7.setTag("amenity");
        question7.setTagValue("gym");
        questionList.add(question7);

        Question question8 = new Question();
        question8.setText("Музеи");
        question8.setTag("tourism");
        question8.setTagValue("museum");
        questionList.add(question8);

        questionRepository.insert(questionList);
    }
}
