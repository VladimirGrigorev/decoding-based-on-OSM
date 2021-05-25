package ru.vsu.gecoding.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeocodingResultDTO {

    private Object searchResult;
    private double latitude;
    private double longitude;
}
