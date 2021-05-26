package ru.vsu.gecoding.service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vsu.gecoding.data.dto.GeocodingResultDTO;
import ru.vsu.gecoding.data.entity.Checkin;
import ru.vsu.gecoding.data.entity.Question;
import ru.vsu.gecoding.data.entity.User;
import ru.vsu.gecoding.data.repository.UserRepository;
import ru.vsu.gecoding.exeption.NotFoundException;

import java.awt.geom.Point2D;
import java.util.ArrayList;

@Service
public class GeocodingService {

    private final UserRepository userRepository;

    public GeocodingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Document findUserLocation(double latitude, double longitude, double accuracy, Authentication authentication) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("project_database");

        Point currentLoc = new Point(new Position(longitude, latitude));

        // геопространственный запрос nearSphere
        FindIterable<Document> searchResults = database.getCollection("voronezh").find(
                Filters.nearSphere("geometry", currentLoc, accuracy, 0.0));

        // выборка только именнованных объектов
        ArrayList<Document> namedObjects = findNamedLocations(searchResults);

        if (authentication != null) {
            // пробуем найти локацию с учетом интересов
            Document location = findLocationByInterest(namedObjects, authentication);
            if (location != null)
                return location;
            else {
                // пробуем найти локацию с учетом истории чек-инов
                location = findLocationByHistory(namedObjects, authentication);
                if (location != null)
                    return location;
            }
        }

        mongoClient.close();

        if (namedObjects.get(0) == null)
            throw new NotFoundException();

        // берем первый ближайший результат
        return namedObjects.get(0);
    }

    public GeocodingResultDTO findCenterPoint(Document data) {
        Document geometry;

        if (data != null)
            geometry = (Document) data.get("geometry");
        else
            throw new NotFoundException();

        if (geometry.get("type").equals("Point")) {
            ArrayList<Double> point = (ArrayList<Double>) geometry.get("coordinates");
            return new GeocodingResultDTO(data, point.get(1), point.get(0));
        } else if (geometry.get("type").equals("LineString")) {
            Point2D.Double result = findExtremesForLineString(geometry);
            return new GeocodingResultDTO(data, result.x, result.y);
        } else if (geometry.get("type").equals("Polygon")) {
            Point2D.Double result = findExtremesForPolygon(geometry);
            return new GeocodingResultDTO(data, result.x, result.y);
        } else if (geometry.get("type").equals("MultiPolygon")) {
            Point2D.Double result = findExtremesForMultiPolygon(geometry);
            return new GeocodingResultDTO(data, result.x, result.y);
        }

        return null;
    }

    private Point2D.Double findExtremesForLineString(Document geometry) {
        ArrayList<ArrayList<Double>> polygon =
                (ArrayList<ArrayList<Double>>) geometry.get("coordinates");

        Double maxX = 0.0;
        Double minX = 0.0;
        Double maxY = 0.0;
        Double minY = 0.0;

        for (int i = 0; i < polygon.size(); i++) {
            minX = (polygon.get(i).get(0) < minX || minX == 0)
                    ? polygon.get(i).get(0) : minX;
            maxX = (polygon.get(i).get(0) > maxX || maxX == 0)
                    ? polygon.get(i).get(0) : maxX;
            minY = (polygon.get(i).get(1) < minY || minY == 0)
                    ? polygon.get(i).get(1) : minY;
            maxY = (polygon.get(i).get(1) > maxY || maxY == 0)
                    ? polygon.get(i).get(1) : maxY;
        }
        return new Point2D.Double((minY + maxY) / 2, (minX + maxX) / 2);
    }

    private Point2D.Double findExtremesForPolygon(Document geometry) {
        ArrayList<ArrayList<ArrayList<Double>>> polygon =
                (ArrayList<ArrayList<ArrayList<Double>>>) geometry.get("coordinates");

        Double maxX = 0.0;
        Double minX = 0.0;
        Double maxY = 0.0;
        Double minY = 0.0;

        for (int i = 0; i < polygon.size(); i++) {
            for (int j = 0; j < polygon.get(i).size(); j++) {
                minX = (polygon.get(i).get(j).get(0) < minX || minX == 0)
                        ? polygon.get(i).get(j).get(0) : minX;
                maxX = (polygon.get(i).get(j).get(0) > maxX || maxX == 0)
                        ? polygon.get(i).get(j).get(0) : maxX;
                minY = (polygon.get(i).get(j).get(1) < minY || minY == 0)
                        ? polygon.get(i).get(j).get(1) : minY;
                maxY = (polygon.get(i).get(j).get(1) > maxY || maxY == 0)
                        ? polygon.get(i).get(j).get(1) : maxY;
            }
        }
        return new Point2D.Double((minY + maxY) / 2, (minX + maxX) / 2);
    }

    private Point2D.Double findExtremesForMultiPolygon(Document geometry) {
        ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> multiPolygon =
                (ArrayList<ArrayList<ArrayList<ArrayList<Double>>>>) geometry.get("coordinates");

        Double maxX = 0.0;
        Double minX = 0.0;
        Double maxY = 0.0;
        Double minY = 0.0;

        for (int i = 0; i < multiPolygon.size(); i++) {
            for (int j = 0; j < multiPolygon.get(i).size(); j++) {
                for (int k = 0; k < multiPolygon.get(i).get(j).size(); k++) {
                    minX = (multiPolygon.get(i).get(j).get(k).get(0) < minX || minX == 0)
                            ? multiPolygon.get(i).get(j).get(k).get(0) : minX;
                    maxX = (multiPolygon.get(i).get(j).get(k).get(0) > maxX || maxX == 0)
                            ? multiPolygon.get(i).get(j).get(k).get(0) : maxX;
                    minY = (multiPolygon.get(i).get(j).get(k).get(1) < minY || minY == 0)
                            ? multiPolygon.get(i).get(j).get(k).get(1) : minY;
                    maxY = (multiPolygon.get(i).get(j).get(k).get(1) > maxY || maxY == 0)
                            ? multiPolygon.get(i).get(j).get(k).get(1) : maxY;
                }
            }
        }
        return new Point2D.Double((minY + maxY) / 2, (minX + maxX) / 2);
    }

    private ArrayList<Document> findNamedLocations(FindIterable<Document> searchResults) {
        ArrayList<Document> namedObjects = new ArrayList<>();
        for (Document location : searchResults) {
            // проверяем присутствует ли тэг name
            if (((Document) location.get("properties")).get("name") != null) {
                namedObjects.add(location);
            }
        }
        return namedObjects;
    }

    private Document findLocationByInterest(ArrayList<Document> namedObjects, Authentication authentication) {
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByName(currentPrincipalName);

        if (user.getInterests() != null) {
            for (Document location : namedObjects) {
                for (Question question : user.getInterests()) {
                    if (((Document) location.get("properties")).get(question.getTag()) != null) {
                        Object tegValue = ((Document) location.get("properties")).get(question.getTag());
                        // сравниваем значение тэга в локации со значением соответсвующего тэга в вопросе
                        if (tegValue.toString().equals(question.getTagValue())) {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Document findLocationByHistory(ArrayList<Document> namedObjects, Authentication authentication) {
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByName(currentPrincipalName);

        if (user.getCheckins() != null) {
            for (Document location : namedObjects) {
                for (Checkin checkin : user.getCheckins()) {
                    if (((Document) location.get("properties")).get("name") != null) {
                        // сравниваем имя локации с именем в локации чек-ина, берем первый ближайший результат
                        if (((Document) location.get("properties")).get("name").equals(checkin.getLocationName())) {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }
}
