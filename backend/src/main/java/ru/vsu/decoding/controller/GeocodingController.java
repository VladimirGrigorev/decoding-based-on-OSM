package ru.vsu.decoding.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import ru.vsu.decoding.data.dto.GeocodingResultDTO;
import ru.vsu.decoding.data.dto.UserLocationDTO;
import ru.vsu.decoding.exeption.NotFoundException;
import ru.vsu.decoding.service.GeocodingService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/geocoding")
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @PostMapping(path = "/find")
    public GeocodingResultDTO findUserLocation(@RequestBody UserLocationDTO userLocationDTO) {

        Document result = geocodingService.geospatialQuery(
                51.6561,
                39.20632,
                500
        );

        GeocodingResultDTO geocodingResultDTO = geocodingService.findCenterPoint(result);
        return geocodingResultDTO;
    }
}
