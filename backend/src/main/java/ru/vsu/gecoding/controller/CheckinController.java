package ru.vsu.gecoding.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.gecoding.data.entity.Checkin;
import ru.vsu.gecoding.service.CheckinService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/checkin")
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @GetMapping
    public List<Checkin> getCheckins(Authentication authentication){
        return checkinService.getAllCheckins(authentication);
    }

    @PostMapping(path = "/create")
    public void createCheckin(@RequestBody String locationName, Authentication authentication){
        checkinService.createCheckin(locationName, authentication);
    }
}
