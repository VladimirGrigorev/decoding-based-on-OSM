package ru.vsu.decoding.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "role")
public class Role {

    @Id
    private BigInteger _id;

    private String name;

    @DBRef(lazy = true)
    private List<User> users;
}
