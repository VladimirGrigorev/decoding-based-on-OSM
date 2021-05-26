package ru.vsu.gecoding.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "checkin")
public class Checkin {

    @Id
    private BigInteger _id;

    private Date date;
    private String locationName;

    public Checkin(Date date, String locationName) {
        this.date = date;
        this.locationName = locationName;
    }
}
