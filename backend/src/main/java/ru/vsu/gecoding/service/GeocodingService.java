package ru.vsu.gecoding.service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.springframework.stereotype.Service;
import ru.vsu.gecoding.data.dto.GeocodingResultDTO;
import ru.vsu.gecoding.exeption.NotFoundException;

import java.awt.geom.Point2D;
import java.util.ArrayList;

@Service
public class GeocodingService {

    public Document geospatialQuery(double latitude, double longitude, double accuracy) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("project_database");

        Point currentLoc = new Point(new Position(longitude, latitude));

        FindIterable<Document> searchResults = database.getCollection("voronezh").find(
                Filters.near("geometry", currentLoc, accuracy, 0.0));

        Document result = searchResults.first();
        mongoClient.close();

        return result;
    }

    public GeocodingResultDTO findCenterPoint(Document data) {
        Document geometry;

        if(data != null)
            geometry = (Document) data.get("geometry");
        else
            throw new NotFoundException();

        if(geometry.get("type").equals("MultiPolygon")){
            Point2D.Double result = findExtremesForMultiPolygon(geometry);

            return new GeocodingResultDTO(data, result.x, result.y);
        }

        return null;
    }

    private Point2D.Double findExtremesForMultiPolygon(Document geometry){
        ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> multiPolygon =
                (ArrayList<ArrayList<ArrayList<ArrayList<Double>>>>) geometry.get("coordinates");

        Double maxX = 0.0; Double minX = 0.0; Double maxY = 0.0; Double minY = 0.0;

        for(int i = 0; i < multiPolygon.size(); i++) {
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
        return new Point2D.Double((minY + maxY) /2, (minX + maxX) /2);
    }
}
