package ru.vsu.gecoding.data.dto;

import lombok.Data;

@Data
public class UserLocationDTO {

    private double latitude;
    private double longitude;
    private double accuracy;
}
