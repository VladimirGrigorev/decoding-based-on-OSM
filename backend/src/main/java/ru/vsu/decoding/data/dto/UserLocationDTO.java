package ru.vsu.decoding.data.dto;

import lombok.Data;

@Data
public class UserLocationDTO {

    private double latitude;
    private double longitude;
    private double accuracy;
}
