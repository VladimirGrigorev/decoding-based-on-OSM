package ru.vsu.gecoding.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vsu.gecoding.data.entity.Checkin;
import ru.vsu.gecoding.data.entity.User;
import ru.vsu.gecoding.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckinService {
    private final UserRepository userRepository;

    public CheckinService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Checkin> getAllCheckins(Authentication authentication) {
        if(authentication != null) {
            String currentPrincipalName = authentication.getName();
            User user = userRepository.findByName(currentPrincipalName);

            return user.getCheckins();
        }
        return null;
    }

    public void createCheckin(String locationName, Authentication authentication){
        if(authentication != null) {
            String currentPrincipalName = authentication.getName();
            User user = userRepository.findByName(currentPrincipalName);

            Checkin checkin = new Checkin(new Date(), locationName);

            ArrayList<Checkin> checkins = (ArrayList<Checkin>) user.getCheckins();

            if(checkins == null)
                checkins = new ArrayList<Checkin>();

            checkins.add(checkin);
            user.setCheckins(checkins);
            userRepository.save(user);
        }
    }
}
