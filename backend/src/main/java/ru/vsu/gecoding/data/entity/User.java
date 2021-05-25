package ru.vsu.gecoding.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private BigInteger _id;

    private String name;
    private String password;
    private String gender;
    private Integer age;

    @DBRef(lazy = true)
    private List<Role> roles;
}
