package ru.vsu.gecoding;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class Application {

	public static void main(String[] args) {
		try
		{
			MongoClient mongoClient = MongoClients.create();
			MongoDatabase database = mongoClient.getDatabase("project_database");

			database.getCollection("voronezh").createIndex(Indexes.geo2dsphere("geometry"));

			mongoClient.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

		SpringApplication.run(Application.class, args);
	}

}
