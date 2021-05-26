package ru.vsu.gecoding.controller;

import org.bson.Document;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.gecoding.data.dto.GeocodingResultDTO;
import ru.vsu.gecoding.data.dto.UserLocationDTO;
import ru.vsu.gecoding.service.GeocodingService;

@RestController
@RequestMapping("/api/v1/geocoding")
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @PostMapping(path = "/find")
    public GeocodingResultDTO findUserLocation(@RequestBody UserLocationDTO userLocationDTO, Authentication authentication) {
        Document result = geocodingService.findUserLocation(
                userLocationDTO.getLatitude(),
                userLocationDTO.getLongitude(),
                userLocationDTO.getAccuracy(),
                authentication
        );

        GeocodingResultDTO geocodingResultDTO = geocodingService.findCenterPoint(result);
        return geocodingResultDTO;
    }
}
